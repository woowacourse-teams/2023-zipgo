package zipgo.review.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.presentation.Auth;
import zipgo.auth.presentation.OptionalAuth;
import zipgo.auth.presentation.dto.AuthDto;
import zipgo.review.application.ReviewQueryService;
import zipgo.review.application.ReviewService;
import zipgo.review.domain.Review;
import zipgo.review.dto.request.CreateReviewRequest;
import zipgo.review.dto.request.GetReviewsRequest;
import zipgo.review.dto.request.UpdateReviewRequest;
import zipgo.review.dto.response.GetReviewMetadataResponse;
import zipgo.review.dto.response.GetReviewResponse;
import zipgo.review.dto.response.GetReviewsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Auth AuthDto authDto,
            @RequestBody @Valid CreateReviewRequest createReviewRequest
    ) {
        Long reviewId = reviewService.createReview(authDto.id(), createReviewRequest);
        return ResponseEntity.created(URI.create("/reviews/" + reviewId)).build();
    }

    @GetMapping
    public ResponseEntity<GetReviewsResponse> getAllReviews(
            @OptionalAuth AuthDto authDto,
            @ModelAttribute @Valid GetReviewsRequest request) {
        GetReviewsResponse reviews = reviewQueryService.getReviews(request.toQueryRequest(authDto.id()));

        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetReviewResponse> getReview(@OptionalAuth AuthDto authDto, @PathVariable Long id) {
        Review review = reviewQueryService.getReview(id);
        return ResponseEntity.ok(GetReviewResponse.from(review, authDto.id()));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> update(
            @Auth AuthDto authDto,
            @PathVariable Long reviewId,
            @RequestBody @Valid UpdateReviewRequest updateReviewRequest
    ) {
        reviewService.updateReview(authDto.id(), reviewId, updateReviewRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(
            @Auth AuthDto authDto,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(authDto.id(), reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metadata")
    public ResponseEntity<GetReviewMetadataResponse> getMetadata() {
        GetReviewMetadataResponse metadata = reviewQueryService.getReviewMetadata();
        return ResponseEntity.ok(metadata);
    }

}
