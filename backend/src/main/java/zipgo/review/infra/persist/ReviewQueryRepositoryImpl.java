package zipgo.review.infra.persist;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zipgo.pet.domain.AgeGroup;
import zipgo.review.application.SortBy;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.QFindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;
import zipgo.review.dto.response.SummaryElement;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;
import static zipgo.pet.domain.QBreeds.breeds;
import static zipgo.pet.domain.QPet.pet;
import static zipgo.review.domain.QAdverseReaction.adverseReaction;
import static zipgo.review.domain.QHelpfulReaction.helpfulReaction;
import static zipgo.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

    private static final Integer PERCENTAGE = 100;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FindReviewsQueryResponse> findReviewsBy(FindReviewsFilterRequest request) {
        Long petFoodId = request.petFoodId();
        int size = request.size();
        Long lastReviewId = request.lastReviewId();
        List<Long> petSizeIds = request.petSizes();
        List<Long> breedIds = request.breedIds();

        return queryFactory.select(new QFindReviewsQueryResponse(
                                review.id,
                                review.rating,
                                review.createdAt,
                                review.comment,
                                review.tastePreference,
                                review.stoolCondition,
                                pet.id,
                                pet.name,
                                pet.imageUrl,
                                pet.birthYear,
                                review.weight,
                                breeds.id,
                                breeds.name,
                                breeds.petSize.id,
                                breeds.petSize.name
                        )
                )
                .from(review)
                .join(review.pet, pet)
                .join(pet.breeds, breeds)
                .where(
                        equalsPetFoodId(petFoodId),
                        afterThan(lastReviewId),
                        inPetSizes(petSizeIds),
                        inBreedIds(breedIds),
                        inAgeGroup(request.ageGroups())
                )
                .orderBy(getSorter(request.sortBy()))
                .limit(size)
                .fetch();
    }

    public List<Review> findReviewWithAdverseReactions(List<Long> reviewIds) {
        return queryFactory.selectFrom(review)
                .join(review.adverseReactions, adverseReaction)
                .fetchJoin()
                .where(review.id.in(reviewIds))
                .fetch();

    }

    private BooleanExpression inBreedIds(List<Long> breedIds) {
        if (breedIds.isEmpty()) {
            return null;
        }
        return breeds.id.in(breedIds);
    }

    private BooleanExpression inPetSizes(List<Long> petSizeIds) {
        if (petSizeIds.isEmpty()) {
            return null;
        }
        return breeds.petSize.id.in(petSizeIds);
    }

    private BooleanExpression inAgeGroup(List<AgeGroup> ageGroups) {
        if (ageGroups.isEmpty()) {
            return null;
        }
        return ageGroups.stream()
                .map(ageGroup -> {
                    BooleanExpression greaterThanEqual = review.pet.birthYear.goe(ageGroup.calculateMinBirthYear());
                    BooleanExpression LessThanEqual = review.pet.birthYear.loe(ageGroup.calculateMaxBirthYear());
                    return greaterThanEqual.and(LessThanEqual);
                })
                .reduce((previous, current) -> Expressions.booleanOperation(Ops.OR, previous, current))
                .orElse(null);
    }

    private BooleanExpression equalsPetFoodId(@NotNull Long petFoodId) {
        return review.petFood.id.eq(petFoodId);
    }

    private BooleanExpression afterThan(Long lastReviewId) {
        if (lastReviewId == null) {
            return null;
        }
        return review.id.lt(lastReviewId);
    }

    private OrderSpecifier getSorter(SortBy sortBy) {
        if (sortBy == SortBy.RECENT) {
            return review.id.desc();
        }
        if (sortBy == SortBy.RAGING_DESC) {
            return review.rating.desc();
        }
        if (sortBy == SortBy.RATING_ASC) {
            return review.rating.asc();
        }
        if (sortBy == SortBy.HELPFUL) {
            return review.helpfulReactions.size().desc();
        }
        return review.id.desc();
    }

    @Override
    public List<ReviewHelpfulReaction> findReviewWithHelpfulReactions(List<Long> reviewIds, Long memberId) {
        List<Long> reviewIdsReactedByMember = findReviewIdsReactedBy(reviewIds, memberId);
        List<Tuple> reviewIdToReactionCount = queryFactory
                .select(review.id, review.helpfulReactions.size())
                .from(review)
                .where(review.id.in(reviewIds))
                .fetch();

        return collect(reviewIdsReactedByMember, reviewIdToReactionCount);
    }

    private List<Long> findReviewIdsReactedBy(List<Long> reviewIds, Long memberId) {
        if (memberId == null) {
            return emptyList();
        }
        return queryFactory
                .select(review.id)
                .from(helpfulReaction)
                .join(helpfulReaction.review, review)
                .where(review.id.in(reviewIds), helpfulReaction.madeBy.id.eq(memberId))
                .fetch();
    }

    private List<ReviewHelpfulReaction> collect(List<Long> reviewIdsReactedByMember,
                                                List<Tuple> reviewIdToReactionCount) {
        return reviewIdToReactionCount.stream().map(tuple -> {
            Long reviewId = tuple.get(review.id);
            Long count = tuple.get(review.helpfulReactions.size()).longValue();
            return new ReviewHelpfulReaction(reviewId, count, reviewIdsReactedByMember.contains(reviewId));
        }).toList();
    }

    @Override
    public double getReviewsAverageRating(Long petFoodId) {
        return queryFactory
                .select(review.rating.avg())
                .from(review)
                .where(equalsPetFoodId(petFoodId))
                .fetchOne();

    }

    @Override
    public List<SummaryElement> getReviewRatingsAverageDistribution(Long petFoodId) {
        Map<Integer, Long> ratingCountMap = queryFactory
                .select(review.rating, review.rating.count())
                .from(review)
                .where(equalsPetFoodId(petFoodId))
                .groupBy(review.rating)
                .fetch()
                .stream()
                .collect(toMap(
                        tuple -> tuple.get(0, Integer.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        Long reviewTotalCount = getReviewTotalCountByReviewId(petFoodId);

        return IntStream.rangeClosed(1, 5)
                .mapToObj(rating -> {
                    Long ratingCount = ratingCountMap.getOrDefault(rating, 0L);
                    Integer percentage = calculatePercentage(ratingCount, reviewTotalCount);
                    return new SummaryElement(String.valueOf(rating), percentage);
                })
                .toList();
    }

    private Integer calculatePercentage(double obtained, double total) {
        return (int) (obtained * PERCENTAGE / total);
    }

    @Override
    public List<SummaryElement> getReviewTastesAverageDistribution(Long petFoodId) {
        Map<TastePreference, Long> ratingCountMap = queryFactory
                .select(review.tastePreference, review.tastePreference.count())
                .from(review)
                .where(equalsPetFoodId(petFoodId))
                .groupBy(review.tastePreference)
                .fetch()
                .stream()
                .collect(toMap(
                        tuple -> tuple.get(0, TastePreference.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        Long reviewTotalCount = getReviewTotalCountByReviewId(petFoodId);

        return Arrays.stream(TastePreference.values())
                .map(tastePreference -> {
                    Long ratingCount = ratingCountMap.getOrDefault(tastePreference, 0L);
                    Integer percentage = calculatePercentage(ratingCount, reviewTotalCount);
                    return new SummaryElement(tastePreference.getDescription(), percentage);
                })
                .toList();
    }

    @Override
    public List<SummaryElement> getReviewStoolConditionAverageDistribution(Long petFoodId) {
        Map<StoolCondition, Long> ratingCountMap = queryFactory
                .select(review.stoolCondition, review.stoolCondition.count())
                .from(review)
                .where(equalsPetFoodId(petFoodId))
                .groupBy(review.stoolCondition)
                .fetch()
                .stream()
                .collect(toMap(
                        tuple -> tuple.get(0, StoolCondition.class),
                        tuple -> tuple.get(1, Long.class)
                ));

        Long reviewTotalCount = getReviewTotalCountByReviewId(petFoodId);

        return Arrays.stream(StoolCondition.values())
                .map(stoolCondition -> {
                    Long ratingCount = ratingCountMap.getOrDefault(stoolCondition, 0L);
                    Integer percentage = calculatePercentage(ratingCount, reviewTotalCount);
                    return new SummaryElement(stoolCondition.getDescription(), percentage);
                })
                .toList();
    }

    @Override
    public List<SummaryElement> getReviewAdverseReactionAverageDistribution(Long petFoodId) {
        List<Review> reviews = queryFactory
                .selectFrom(review)
                .where(equalsPetFoodId(petFoodId))
                .fetch();

        Map<AdverseReactionType, Long> reactionCountMap = reviews.stream()
                .flatMap(review -> review.getAdverseReactions().stream())
                .collect(Collectors.groupingBy(
                        AdverseReaction::getAdverseReactionType,
                        Collectors.counting()
                ));

        int totalAdverseReactions = reviews.stream()
                .mapToInt(review -> review.getAdverseReactions().size())
                .sum();

        return Arrays.stream(AdverseReactionType.values())
                .map(reactionType -> {
                    Long reactionCount = reactionCountMap.getOrDefault(reactionType, 0L);
                    Integer percentage = calculatePercentage(reactionCount, totalAdverseReactions);
                    return new SummaryElement(reactionType.getDescription(), percentage);
                })
                .toList();
    }

    private Long getReviewTotalCountByReviewId(Long petFoodId) {
        Long reviewTotalCount = queryFactory
                .select(review.count())
                .from(review)
                .where(equalsPetFoodId(petFoodId))
                .fetchOne();
        return reviewTotalCount;
    }

}
