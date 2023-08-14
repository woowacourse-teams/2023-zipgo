package zipgo.review.dto.response;

import java.util.List;
import zipgo.review.domain.Review;

import static java.time.format.DateTimeFormatter.ofPattern;

public record GetReviewResponse(
        Long id,
        String reviewerName,
        int rating,
        String date,
        String comment,
        String tastePreference,
        String stoolCondition,
        List<String> adverseReactions,
        PetProfileResponse petProfile,
        HelpfulReactionResponse helpfulReaction

) {

    public record PetProfileResponse(
            Long id,
            String name,
            int writtenAge,
            double writtenWeight,
            BreedResponse breed
    ) {

    }

    public record BreedResponse(Long id, String name, PetSizeResponse size) {

    }

    public record PetSizeResponse(Long id, String name) {

    }

    public record HelpfulReactionResponse(Long count, boolean reacted) {

    }

    public static GetReviewResponse from(Review review) {
        return new GetReviewResponse(
                review.getId(),
                review.getPet().getOwner().getName(),
                review.getRating(),
                review.getCreatedAt().format(ofPattern("yyyy-MM-dd")),
                review.getComment(),
                review.getTastePreference().getDescription(),
                review.getStoolCondition().getDescription(),
                getAdverseReactions(review),
                null,
                null
        );
    }

    private static List<String> getAdverseReactions(Review review) {
        return review.getAdverseReactions().stream()
                .map(adverseReaction -> adverseReaction.getAdverseReactionType().getDescription())
                .toList();
    }

}
