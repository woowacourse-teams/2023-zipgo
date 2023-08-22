package zipgo.review.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.dto.response.GetReviewMetadataResponse;
import zipgo.review.dto.response.GetReviewMetadataResponse.Metadata;
import zipgo.review.dto.response.GetReviewsResponse;
import zipgo.review.dto.response.GetReviewsSummaryResponse;
import zipgo.review.dto.response.RatingSummaryElement;
import zipgo.review.dto.response.SummaryElement;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private static final int PERCENTAGE = 100;

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final PetFoodQueryRepository petFoodQueryRepository;
    private final BreedsRepository breedsRepository;
    private final PetSizeRepository petSizeRepository;

    public GetReviewsResponse getReviews(FindReviewsFilterRequest request) {
        List<FindReviewsQueryResponse> reviews = reviewQueryRepository.findReviewsBy(request);

        Map<Long, List<String>> reviewIdToAdverseReactions = findAdverseReactionsBy(reviews);
        Map<Long, ReviewHelpfulReaction> reviewIdToHelpfulReactions = findHelpfulReactionsBy(request.memberId(),
                reviews);

        return GetReviewsResponse.of(reviews, reviewIdToAdverseReactions, reviewIdToHelpfulReactions);
    }

    private Map<Long, ReviewHelpfulReaction> findHelpfulReactionsBy(Long memberId,
                                                                    List<FindReviewsQueryResponse> reviews) {
        List<Long> reviewIds = reviews.stream().map(FindReviewsQueryResponse::id).toList();
        return reviewQueryRepository.findReviewWithHelpfulReactions(
                        reviewIds, memberId).stream()
                .collect(toMap(ReviewHelpfulReaction::reviewId, Function.identity()));
    }

    private Map<Long, List<String>> findAdverseReactionsBy(List<FindReviewsQueryResponse> reviews) {
        List<Long> reviewIds = reviews.stream().map(FindReviewsQueryResponse::id).toList();
        return reviewQueryRepository.findReviewWithAdverseReactions(reviewIds)
                .stream()
                .collect(toMap(Review::getId, review -> review.getAdverseReactions().stream()
                        .map(adverse -> adverse.getAdverseReactionType().getDescription())
                        .toList()));
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.getById(reviewId);
    }

    public GetReviewMetadataResponse getReviewMetadata() {
        List<Metadata> breedSizes = findAllBreedSizes();
        List<Metadata> sorters = findAllSorters();
        List<Metadata> ageGroups = findAllAgeGroups();
        List<Metadata> breeds = findAllBreeds();

        return new GetReviewMetadataResponse(breedSizes, sorters, ageGroups, breeds);
    }

    private List<Metadata> findAllBreeds() {
        return breedsRepository.findAll().stream()
                .map(breed -> new Metadata(breed.getId(), breed.getName()))
                .toList();
    }

    private List<Metadata> findAllAgeGroups() {
        return Arrays.stream(AgeGroup.values())
                .map(ageGroup -> new Metadata(ageGroup.getId(), ageGroup.getName()))
                .toList();
    }

    private List<Metadata> findAllSorters() {
        return Arrays.stream(SortBy.values())
                .map(sortBy -> new Metadata(sortBy.getId(), sortBy.getName()))
                .toList();
    }

    private List<Metadata> findAllBreedSizes() {
        return petSizeRepository.findAll()
                .stream()
                .map(petSize -> new Metadata(petSize.getId(), petSize.getName()))
                .toList();
    }

    // rating(평균 별점)
    // 1점~5점까지 각 점수별 평균 별점 %
    // 1점~5점까지 각 점수별 평균 입맛 %
    // 1점~5점까지 각 점수별 평균 똥 %
    // 1점~5점까지 각 점수별 평균 이상 반응 %
    public GetReviewsSummaryResponse getReviewsSummary(Long petFoodId) {
        PetFood petFood = petFoodQueryRepository.findPetFoodWithReviewsByPetFoodId(petFoodId);
        List<Review> reviews = petFood.getReviews().getReviews();

        int reviewSize = reviews.size();

        RatingSummaryElement ratingSummary = getSummarizeRating(petFoodId);
//        List<SummaryElement> tastePreference = reviewQueryRepository.getReviewTastesAverageDistribution(petFoodId);
        List<SummaryElement> stoolCondition = summarizeStoolCondition(reviews, reviewSize);
        List<SummaryElement> adverseReaction = summarizeAdverseReaction(reviews);

        return GetReviewsSummaryResponse.of(ratingSummary, tastePreference, stoolCondition, adverseReaction);
    }

    private RatingSummaryElement getSummarizeRating(Long petFoodId) {
        double reviewsAverageRating = reviewQueryRepository.getReviewsAverageRating(petFoodId);
        List<SummaryElement> summaryElements = reviewQueryRepository.getReviewRatingsAverageDistribution(petFoodId);
        return new RatingSummaryElement(reviewsAverageRating, summaryElements);
    }

    private List<SummaryElement> summarizeStoolCondition(List<Review> reviews, int reviewSize) {
        List<SummaryElement> stoolCondition = new ArrayList<>();
        Map<StoolCondition, List<Review>> groupByStoolCondition = reviews.stream()
                .collect(Collectors.groupingBy(Review::getStoolCondition));

        StoolCondition[] values = StoolCondition.values();
        for (StoolCondition value : values) {
            stoolCondition.add(new SummaryElement(value.getDescription(),
                    getZeroOrStoolConditionPercentage(reviewSize, groupByStoolCondition, value)));
        }
        return stoolCondition;
    }

    private int getZeroOrStoolConditionPercentage(int reviewSize,
                                                  Map<StoolCondition, List<Review>> groupByStoolCondition,
                                                  StoolCondition value) {
        if (reviewSize == 0) {
            return 0;
        }
        return groupByStoolCondition.getOrDefault(value, new ArrayList<>()).size() * PERCENTAGE / reviewSize;
    }

    private List<SummaryElement> summarizeAdverseReaction(List<Review> reviews) {
        List<SummaryElement> adverseReaction = new ArrayList<>();

        List<AdverseReaction> allAdverseReaction = reviews.stream()
                .map(Review::getAdverseReactions)
                .flatMap(Collection::stream)
                .toList();
        Map<AdverseReactionType, List<AdverseReaction>> groupByAdverseReaction = allAdverseReaction.stream()
                .collect(Collectors.groupingBy(AdverseReaction::getAdverseReactionType));

        AdverseReactionType[] values = AdverseReactionType.values();
        for (AdverseReactionType value : values) {
            adverseReaction.add(new SummaryElement(value.getDescription(),
                    getZeroOrAdverseReactionPercentage(allAdverseReaction, groupByAdverseReaction, value)));
        }
        return adverseReaction;
    }

    private int getZeroOrAdverseReactionPercentage(List<AdverseReaction> allAdverseReaction,
                                                   Map<AdverseReactionType, List<AdverseReaction>> groupByAdverseReaction,
                                                   AdverseReactionType value) {
        if (allAdverseReaction.size() == 0) {
            return 0;
        }
        return groupByAdverseReaction.getOrDefault(value, new ArrayList<>()).size() * PERCENTAGE
                / allAdverseReaction.size();
    }

}
