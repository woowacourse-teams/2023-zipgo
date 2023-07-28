package zipgo.review.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.Review;

public record GetReviewsResponse(
        Long id,
        String reviwerName,
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
                //TODO 소셜로그인에서 이름 추가되면 리팩터링 예정
                "작성자",
                review.getRating(),
                review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                review.getComment(),
                review.getStoolCondition().getValue(),
                review.getTastePreference().getValue(),
                getAdverseReactions(review)
        );
    }

    private static List<String> getAdverseReactions(Review review) {
        return review.getAdverseReactions().stream()
                .map(AdverseReaction::getName)
                .toList();
    }

}
