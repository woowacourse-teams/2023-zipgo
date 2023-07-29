package zipgo.review.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipgo.review.application.ReviewQueryService;
import zipgo.review.application.ReviewService;
import zipgo.review.domain.Review;
import zipgo.review.dto.request.CreateReviewRequest;
import zipgo.review.dto.request.UpdateReviewRequest;
import zipgo.review.dto.response.GetReviewsResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Void> create(
            Long memberId,
            @RequestBody @Valid CreateReviewRequest createReviewRequest
    ) {
        Long reviewId = reviewService.createReview(memberId, createReviewRequest);
        return ResponseEntity.created(URI.create("/reviews/" + reviewId)).build();
    }

    @GetMapping("/pet-foods/{petFoodId}/reviews")
    public ResponseEntity<List<GetReviewsResponse>> getAllReviews(@PathVariable Long petFoodId) {
        List<Review> reviews = reviewQueryService.getAllReviews(petFoodId);
        List<GetReviewsResponse> reviewsResponses = reviews.stream()
                .map(GetReviewsResponse::from)
                .toList();
        return ResponseEntity.ok(reviewsResponses);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> update(
            Long memberId,
            @PathVariable Long reviewId,
            @RequestBody @Valid UpdateReviewRequest updateReviewRequest
    ) {

        reviewService.updateReview(memberId, reviewId, updateReviewRequest);
        return ResponseEntity.noContent().build();
    }

}
