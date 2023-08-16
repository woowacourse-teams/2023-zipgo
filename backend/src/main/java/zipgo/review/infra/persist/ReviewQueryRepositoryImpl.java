package zipgo.review.infra.persist;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import zipgo.pet.domain.AgeGroup;
import zipgo.review.application.SortBy;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.dto.FindReviewsQueryRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.QFindReviewsQueryResponse;

import static zipgo.pet.domain.QBreeds.breeds;
import static zipgo.pet.domain.QPet.pet;
import static zipgo.review.domain.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {

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

}
