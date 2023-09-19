package zipgo.review.application;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.Breed;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.repository.BreedRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.Reviews;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;
import zipgo.review.dto.response.GetReviewMetadataResponse;
import zipgo.review.dto.response.GetReviewMetadataResponse.Metadata;
import zipgo.review.dto.response.GetReviewsResponse;
import zipgo.review.dto.response.GetReviewsSummaryResponse;
import zipgo.review.dto.response.type.AdverseReactionResponse;
import zipgo.review.dto.response.type.RatingInfoResponse;
import zipgo.review.dto.response.type.RatingSummaryResponse;
import zipgo.review.dto.response.type.StoolConditionResponse;
import zipgo.review.dto.response.type.TastePreferenceResponse;

import static java.util.stream.Collectors.toMap;
import static zipgo.pet.domain.Breeds.MIXED_BREED_ID;
import static zipgo.pet.domain.Breeds.MIXED_BREED_NAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final PetFoodRepository petFoodRepository;
    private final BreedRepository breedRepository;
    private final PetSizeRepository petSizeRepository;

    public GetReviewsResponse getReviews(FindReviewsFilterRequest request) {
        List<Long> mixBreedIds = findMixedBreedIds();
        if (isRequestContainMixBreeds(request.breedIds(), mixBreedIds)) {
            request = request.toMixedBreedRequest(mixBreedIds);
        }

        List<FindReviewsQueryResponse> reviews = reviewQueryRepository.findReviewsBy(request);

        Map<Long, List<String>> reviewIdToAdverseReactions = findAdverseReactionsBy(reviews);
        Map<Long, ReviewHelpfulReaction> reviewIdToHelpfulReactions = findHelpfulReactionsBy(request.memberId(), reviews);

        return GetReviewsResponse.of(reviews, reviewIdToAdverseReactions, reviewIdToHelpfulReactions);
    }

    private List<Long> findMixedBreedIds() {
        return breedRepository.findByName(MIXED_BREED_NAME).stream()
                .map(Breed::getId)
                .toList();
    }

    private boolean isRequestContainMixBreeds(List<Long> requestIds, List<Long> mixBreedIds) {
        if (!requestIds.isEmpty() && requestIds.get(0) == MIXED_BREED_ID) {
            return true;
        }
        if (!requestIds.isEmpty() && mixBreedIds.contains(requestIds.get(0))) {
            return true;
        }
        return false;
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
        Breeds breeds = Breeds.from(breedRepository.findAll());
        return breeds.getOrderedBreeds().stream()
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

    public GetReviewsSummaryResponse getReviewsSummary(Long petFoodId) {
        PetFood petFood = petFoodRepository.getById(petFoodId);
        Reviews reviews = petFood.getReviews();

        RatingSummaryResponse ratingsSummary = getRatingsSummary(reviews);
        List<TastePreferenceResponse> tastesSummary = getTastesSummary(reviews);
        List<StoolConditionResponse> stoolConditionsSummary = getStoolsSummary(reviews);
        List<AdverseReactionResponse> adverseReactionsSummary = getAdverseReactions(reviews);

        return GetReviewsSummaryResponse.of(ratingsSummary, tastesSummary, stoolConditionsSummary, adverseReactionsSummary);
    }

    private RatingSummaryResponse getRatingsSummary(Reviews reviews) {
        double averageRating = reviews.calculateRatingAverage();
        List<RatingInfoResponse> ratingInfoResponses = IntStream.rangeClosed(MIN_RATING, MAX_RATING)
                .mapToObj(rating -> {
                    int percentage = reviews.getRatingPercentage(rating);
                    return RatingInfoResponse.of(rating, percentage);
                })
                .toList();
        return RatingSummaryResponse.of(averageRating, ratingInfoResponses);
    }

    private List<TastePreferenceResponse> getTastesSummary(Reviews reviews) {
        return Arrays.stream(TastePreference.values())
                .map(tastePreference -> {
                    int percentage = reviews.getTastesPercentage(tastePreference);
                    return TastePreferenceResponse.of(tastePreference, percentage);
                })
                .toList();
    }

    private List<StoolConditionResponse> getStoolsSummary(Reviews reviews) {
        return Arrays.stream(StoolCondition.values())
                .map(stoolCondition -> {
                    int percentage = reviews.getStoolsConditionPercentage(stoolCondition);
                    return StoolConditionResponse.of(stoolCondition, percentage);
                })
                .toList();
    }

    private List<AdverseReactionResponse> getAdverseReactions(Reviews reviews) {
        return Arrays.stream(AdverseReactionType.values())
                .map(adverseReactionType -> {
                    int percentage = reviews.getAdverseReactionPercentage(adverseReactionType);
                    return AdverseReactionResponse.of(adverseReactionType, percentage);
                })
                .toList();
    }

}
