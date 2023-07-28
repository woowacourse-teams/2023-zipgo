package zipgo.review.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import zipgo.review.application.ReviewQueryService;
import zipgo.review.domain.Review;
import zipgo.review.dto.response.GetReviewsResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewQueryService reviewQueryService;

    @GetMapping("/pet-foods/{petFoodId}/reviews")
    public ResponseEntity<List<GetReviewsResponse>> getAllReviews(@PathVariable Long petFoodId) {
        List<Review> reviews = reviewQueryService.getAllReviews(petFoodId);
        List<GetReviewsResponse> reviewsResponses = reviews.stream()
                .map(GetReviewsResponse::from)
                .toList();
        return ResponseEntity.ok(reviewsResponses);
    }

}
