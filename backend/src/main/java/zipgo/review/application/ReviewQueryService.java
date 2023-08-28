package zipgo.review.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;
import zipgo.review.domain.type.AdverseReactionType;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;
import zipgo.review.presentation.dto.response.GetReviewMetadataResponse;
import zipgo.review.presentation.dto.response.GetReviewMetadataResponse.Metadata;
import zipgo.review.presentation.dto.response.GetReviewsResponse;
import zipgo.review.presentation.dto.response.GetReviewsSummaryResponse;
import zipgo.review.presentation.dto.response.type.AdverseReactionResponse;
import zipgo.review.presentation.dto.response.type.RatingInfoResponse;
import zipgo.review.presentation.dto.response.type.RatingSummaryResponse;
import zipgo.review.presentation.dto.response.type.StoolConditionResponse;
import zipgo.review.presentation.dto.response.type.TastePreferenceResponse;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private static final int PERCENTAGE = 100;
    private static final double DEFAULT_PERCENTAGE = 0.0;

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
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

    public GetReviewsSummaryResponse getReviewsSummary(Long petFoodId) {
        List<Review> reviews = reviewQueryRepository.findReviewsBy(petFoodId);

        RatingSummaryResponse ratingsSummary = getRatingsSummary(reviews);
        List<TastePreferenceResponse> tastesSummary = getTastesSummary(reviews);
        List<StoolConditionResponse> stoolConditionsSummary = getStoolsSummary(reviews);
        List<AdverseReactionResponse> adverseReactionsSummary = getAdverseReactionsSummary(reviews);

        return GetReviewsSummaryResponse.of(ratingsSummary, tastesSummary, stoolConditionsSummary, adverseReactionsSummary);
    }

    private RatingSummaryResponse getRatingsSummary(List<Review> reviews) {
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(DEFAULT_PERCENTAGE);

        Map<Integer, Long> ratingAndCount = reviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getRating,
                        Collectors.counting()
                ));

        List<RatingInfoResponse> ratingInfoResponses = IntStream.rangeClosed(1, 5) // Assuming ratings are from 1 to 5
                .mapToObj(rating -> {
                    long count = ratingAndCount.getOrDefault(rating, 0L);
                    int percentage = calculatePercentage(reviews.size(), count);
                    return RatingInfoResponse.of(rating, percentage);
                })
                .toList();
        return RatingSummaryResponse.of(averageRating, ratingInfoResponses);
    }

    private int calculatePercentage(int totalReviews, long count) {
        if (totalReviews == 0) {
            return 0;
        }
        return (int) ((count * PERCENTAGE) / totalReviews);
    }

    private List<TastePreferenceResponse> getTastesSummary(List<Review> reviews) {
        Map<String, Long> tasteAndCount = reviews.stream()
                .collect(groupingBy(
                        review -> review.getReviewPetInfo().getTastePreference().name(),
                        counting()
                ));
        return Arrays.stream(TastePreference.values())
                .map(tastePreference -> {
                    long count = tasteAndCount.getOrDefault(tastePreference.name(), 0L);
                    int percentage = TastePreference.getDistributionPercentage(reviews.size(), count);
                    return TastePreferenceResponse.of(tastePreference, percentage);
                })
                .toList();
    }

    private List<StoolConditionResponse> getStoolsSummary(List<Review> reviews) {
        Map<String, Long> stoolAndCount = reviews.stream()
                .collect(groupingBy(
                        review -> review.getReviewPetInfo().getStoolCondition().name(),
                        counting()
                ));
        return Arrays.stream(StoolCondition.values())
                .map(stoolCondition -> {
                    long count = stoolAndCount.getOrDefault(stoolCondition.name(), 0L);
                    int percentage = StoolCondition.getDistributionPercentage(reviews.size(), count);
                    return StoolConditionResponse.of(stoolCondition, percentage);
                })
                .toList();
    }

    private List<AdverseReactionResponse> getAdverseReactionsSummary(List<Review> reviews) {
        Map<AdverseReactionType, Long> reactionTypeAndCount = new HashMap<>();

        for (Review review : reviews) {
            for (AdverseReaction reaction : review.getAdverseReactions()) {
                AdverseReactionType reactionType = reaction.getAdverseReactionType();
                reactionTypeAndCount.put(reactionType, reactionTypeAndCount.getOrDefault(reactionType, 0L) + 1);
            }
        }

        List<AdverseReactionResponse> reactionResponses = new ArrayList<>();
        int totalReactions = reviews.stream().mapToInt(review -> review.getAdverseReactions().size()).sum();

        for (AdverseReactionType reactionType : AdverseReactionType.values()) {
            long count = reactionTypeAndCount.getOrDefault(reactionType, 0L);
            int percentage = AdverseReactionType.getDistributionPercentage(totalReactions, count);
            reactionResponses.add(AdverseReactionResponse.of(reactionType, percentage));
        }

        return reactionResponses;
    }

}
