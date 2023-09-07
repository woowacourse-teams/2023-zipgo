package zipgo.review.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
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
import zipgo.auth.dto.AuthCredentials;
import zipgo.review.application.ReviewQueryService;
import zipgo.review.application.ReviewService;
import zipgo.review.domain.Review;
import zipgo.review.dto.request.CreateReviewRequest;
import zipgo.review.dto.request.GetReviewsRequest;
import zipgo.review.dto.request.UpdateReviewRequest;
import zipgo.review.dto.response.GetReviewMetadataResponse;
import zipgo.review.dto.response.GetReviewResponse;
import zipgo.review.dto.response.GetReviewsResponse;
import zipgo.review.dto.response.GetReviewsSummaryResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Auth AuthCredentials authCredentials,
            @RequestBody @Valid CreateReviewRequest createReviewRequest
    ) {
        Long reviewId = reviewService.createReview(authCredentials.id(), createReviewRequest);
        return ResponseEntity.created(URI.create("/reviews/" + reviewId)).build();
    }

    @GetMapping
    public ResponseEntity<GetReviewsResponse> getAllReviews(
            @OptionalAuth AuthCredentials authCredentials,
            @ModelAttribute @Valid GetReviewsRequest request) {
        Long memberId = getMemberId(authCredentials);
        GetReviewsResponse reviews = reviewQueryService.getReviews(request.toQueryRequest(memberId));

        return ResponseEntity.ok(reviews);
    }

    private Long getMemberId(AuthCredentials authCredentials) {
        return Optional.ofNullable(authCredentials).map(AuthCredentials::id).orElse(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetReviewResponse> getReview(@OptionalAuth AuthCredentials authCredentials, @PathVariable Long id) {
        Review review = reviewQueryService.getReview(id);
        Long memberId = getMemberId(authCredentials);
        return ResponseEntity.ok(GetReviewResponse.from(review, memberId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> update(
            @Auth AuthCredentials authCredentials,
            @PathVariable Long reviewId,
            @RequestBody @Valid UpdateReviewRequest updateReviewRequest
    ) {
        reviewService.updateReview(authCredentials.id(), reviewId, updateReviewRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(
            @Auth AuthCredentials authCredentials,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(authCredentials.id(), reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/metadata")
    public ResponseEntity<GetReviewMetadataResponse> getMetadata() {
        GetReviewMetadataResponse metadata = reviewQueryService.getReviewMetadata();
        return ResponseEntity.ok(metadata);
    }

    @PostMapping("/{reviewId}/helpful-reactions")
    public ResponseEntity<Void> createHelpfulReaction(
            @Auth AuthCredentials authCredentials,
            @PathVariable Long reviewId
    ) {
        reviewService.addHelpfulReaction(authCredentials.id(), reviewId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}/helpful-reactions")
    public ResponseEntity<Void> deleteHelpfulReaction(
            @Auth AuthCredentials authCredentials,
            @PathVariable Long reviewId
    ) {
        reviewService.removeHelpfulReaction(authCredentials.id(), reviewId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/summary")
    public ResponseEntity<GetReviewsSummaryResponse> getReviewsSummary(Long petFoodId) {
        GetReviewsSummaryResponse reviewsSummary = reviewQueryService.getReviewsSummary(petFoodId);
        return ResponseEntity.ok(reviewsSummary);
    }

}
