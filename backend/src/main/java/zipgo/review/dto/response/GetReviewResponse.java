package zipgo.review.dto.response;

import java.util.List;
import lombok.Builder;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;

import static java.time.format.DateTimeFormatter.ofPattern;

@Builder
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

    @Builder
    public record PetProfileResponse(
            Long id,
            String name,
            int writtenAge,
            double writtenWeight,
            BreedResponse breed
    ) {

    }

    @Builder
    public record BreedResponse(Long id, String name, PetSizeResponse size) {

    }

    @Builder
    public record PetSizeResponse(Long id, String name) {

    }

    @Builder
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

    public static GetReviewResponse of(FindReviewsQueryResponse review, List<String> adverseReactions,
                                       ReviewHelpfulReaction helpfulReaction) {

        PetSizeResponse petSize = PetSizeResponse.builder().id(review.petSizeId()).name(review.petSizeName()).build();
        BreedResponse breed = BreedResponse.builder().id(review.breedId()).name(review.breedName()).size(petSize)
                .build();
        PetProfileResponse petProfile = PetProfileResponse.builder().name(review.petName()).breed(breed).build();
        HelpfulReactionResponse helpful = HelpfulReactionResponse.builder()
                .count(helpfulReaction.helpfulReactionCount()).reacted(helpfulReaction.reacted()).build();

        return GetReviewResponse.builder()
                .id(review.id())
                .adverseReactions(adverseReactions)
                .comment(review.comment())
                .date(review.date().format(ofPattern("yyyy-MM-dd")))
                .rating(review.rating())
                .stoolCondition(review.stoolCondition().getDescription())
                .petProfile(petProfile)
                .helpfulReaction(helpful)
                .build();
    }

    private static List<String> getAdverseReactions(Review review) {
        return review.getAdverseReactions().stream()
                .map(adverseReaction -> adverseReaction.getAdverseReactionType().getDescription())
                .toList();
    }

}
