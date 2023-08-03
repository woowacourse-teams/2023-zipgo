package zipgo.review.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.presentation.Auth;
import zipgo.auth.presentation.dto.AuthDto;
import zipgo.review.application.ReviewQueryService;
import zipgo.review.application.ReviewService;
import zipgo.review.domain.Review;
import zipgo.review.dto.request.CreateReviewRequest;
import zipgo.review.dto.request.UpdateReviewRequest;
import zipgo.review.dto.response.GetReviewResponse;
import zipgo.review.dto.response.GetReviewsResponse;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Void> create(
            @Auth AuthDto authDto,
            @RequestBody @Valid CreateReviewRequest createReviewRequest
    ) {
        Long reviewId = reviewService.createReview(authDto.id(), createReviewRequest);
        return ResponseEntity.created(URI.create("/reviews/" + reviewId)).build();
    }

    //TODO petFoodId 쿼러파라미터로 변경
    @GetMapping("/pet-foods/{petFoodId}/reviews")
    public ResponseEntity<GetReviewsResponse> getAllReviews(@PathVariable Long petFoodId) {
        List<Review> reviews = reviewQueryService.getAllReviews(petFoodId);
        List<GetReviewResponse> reviewsResponses = reviews.stream()
                .map(GetReviewResponse::from)
                .toList();

        return ResponseEntity.ok(GetReviewsResponse.from(reviewsResponses));
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<GetReviewResponse> getReview(@PathVariable Long id) {
        Review review = reviewQueryService.getReview(id);
        return ResponseEntity.ok(GetReviewResponse.from(review));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> update(
            @Auth AuthDto authDto,
            @PathVariable Long reviewId,
            @RequestBody @Valid UpdateReviewRequest updateReviewRequest
    ) {
        reviewService.updateReview(authDto.id(), reviewId, updateReviewRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> delete(
            @Auth AuthDto authDto,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(authDto.id(), reviewId);
        return ResponseEntity.noContent().build();
    }

}
