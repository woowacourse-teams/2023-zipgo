package zipgo.review.dto.response;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.util.List;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;

public record GetReviewsResponse(
        Long id,
        String reviewerName,
        int rating,
        String date,
        String comment,
        String tastePreference,
        String stoolCondition,
        List<String> adverseReactions
) {

    public static GetReviewsResponse from(Review review) {
        return new GetReviewsResponse(
                review.getId(),
                review.getMember().getName(),
                review.getRating(),
                review.getCreatedAt().format(ofPattern("yyyy-MM-dd")),
                review.getComment(),
                review.getStoolCondition().getDescription(),
                review.getTastePreference().getDescription(),
                getAdverseReactions(review)
        );
    }

    private static List<String> getAdverseReactions(Review review) {
        return review.getAdverseReactions().stream()
                .map(adverseReaction -> adverseReaction.getAdverseReactionName().getDescription())
                .toList();
    }

}
