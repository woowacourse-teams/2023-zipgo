package zipgo.review.domain.repository.dto;

public record ReviewHelpfulReaction(
        Long reviewId,
        Long helpfulReactionCount,
        boolean reacted
) {

}
