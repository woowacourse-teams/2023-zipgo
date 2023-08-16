package zipgo.review.application;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.domain.repository.dto.FindReviewsFilterRequest;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;
import zipgo.review.dto.response.GetReviewMetadataResponse;
import zipgo.review.dto.response.GetReviewMetadataResponse.Metadata;
import zipgo.review.dto.response.GetReviewsResponse;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

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

}
