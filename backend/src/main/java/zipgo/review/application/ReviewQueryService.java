package zipgo.review.application;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.review.application.dto.GetReviewQueryRequest;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.ReviewQueryRepository;
import zipgo.review.domain.repository.ReviewRepository;
import zipgo.review.dto.response.GetReviewMetadataResponse;
import zipgo.review.dto.response.GetReviewMetadataResponse.Metadata;
import zipgo.review.dto.response.GetReviewsResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final BreedsRepository breedsRepository;
    private final PetSizeRepository petSizeRepository;

    public GetReviewsResponse getReviews(GetReviewQueryRequest dto) {
        List<Review> reviews = reviewQueryRepository.findReviewsBy(dto.petFoodId(), dto.size(), dto.lastReviewId());
        return GetReviewsResponse.from(reviews);
    }

    public Review getReview(Long reviewId) {
        return reviewRepository.getById(reviewId);
    }

    public GetReviewMetadataResponse getReviewMetadata() {
        return new GetReviewMetadataResponse(
                petSizeRepository.findAll().stream().map(petSize -> new Metadata(petSize.getId(), petSize.getName()))
                        .toList(),
                Arrays.stream(SortBy.values()).map(sortBy -> new Metadata(sortBy.getId(), sortBy.getName())).toList(),
                Arrays.stream(AgeGroup.values()).map(ageGroup -> new Metadata(ageGroup.getId(), ageGroup.getName()))
                        .toList(),
                breedsRepository.findAll().stream().map(breed -> new Metadata(breed.getId(), breed.getName())).toList()
        );
    }

}
