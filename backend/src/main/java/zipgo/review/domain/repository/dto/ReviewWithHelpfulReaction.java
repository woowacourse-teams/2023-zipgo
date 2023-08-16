package zipgo.review.domain.repository.dto;

public record ReviewWithHelpfulReaction(
        Long reviewId,
        Long helpfulReactionCount,
        boolean reacted
) {

}
