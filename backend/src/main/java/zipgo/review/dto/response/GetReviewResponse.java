package zipgo.review.dto.response;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.util.List;
import zipgo.review.domain.Review;

public record GetReviewResponse(
        Long id,
        String reviewerName,
        int rating,
        String date,
        String comment,
        String tastePreference,
        String stoolCondition,
        List<String> adverseReactions
) {

    public static GetReviewResponse from(Review review) {
        return new GetReviewResponse(
                review.getId(),
                review.getMember().getName(),
                review.getRating(),
                review.getCreatedAt().format(ofPattern("yyyy-MM-dd")),
                review.getComment(),
                review.getTastePreference().getDescription(),
                review.getStoolCondition().getDescription(),
                getAdverseReactions(review)
        );
    }

    private static List<String> getAdverseReactions(Review review) {
        return review.getAdverseReactions().stream()
                .map(adverseReaction -> adverseReaction.getAdverseReactionType().getDescription())
                .toList();
    }

}
