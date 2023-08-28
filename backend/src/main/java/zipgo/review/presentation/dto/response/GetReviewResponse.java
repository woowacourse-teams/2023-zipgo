package zipgo.review.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import zipgo.review.domain.Review;
import zipgo.review.domain.repository.dto.FindReviewsQueryResponse;
import zipgo.review.domain.repository.dto.ReviewHelpfulReaction;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.time.format.DateTimeFormatter.ofPattern;

@Builder
@JsonInclude(NON_NULL)
public record GetReviewResponse(
        Long id,
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
            String profileUrl,
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

    public static GetReviewResponse from(Review review, Long memberId) {
        BreedResponse breedResponse = BreedResponse.builder()
                .id(review.getPet().getBreeds().getId())
                .name(review.getPet().getBreeds().getName())
                .size(PetSizeResponse.builder()
                        .id(review.getPet().getBreeds().getPetSize().getId())
                        .name(review.getPet().getBreeds().getPetSize().getName())
                        .build()).build();
        PetProfileResponse petProfileResponse = PetProfileResponse.builder()
                .id(review.getPet().getId())
                .name(review.getPet().getName())
                .profileUrl(review.getPet().getImageUrl())
                .breed(breedResponse)
                .writtenAge(review.getPetAge())
                .writtenWeight(review.getReviewPetInfo().getWeight())
                .build();
        HelpfulReactionResponse helpfulReactionResponse = HelpfulReactionResponse.builder()
                .count((long) review.getHelpfulReactions().size())
                .reacted(review.isReactedBy(memberId))
                .build();

        return new GetReviewResponse(
                review.getId(),
                review.getRating(),
                review.getCreatedAt().format(ofPattern("yyyy-MM-dd")),
                review.getComment(),
                review.getReviewPetInfo().getTastePreference().getDescription(),
                review.getReviewPetInfo().getStoolCondition().getDescription(),
                getAdverseReactions(review),
                petProfileResponse,
                helpfulReactionResponse
        );
    }

    public static GetReviewResponse of(FindReviewsQueryResponse review, List<String> adverseReactions,
                                       ReviewHelpfulReaction helpfulReaction) {

        PetSizeResponse petSize = PetSizeResponse.builder().id(review.petSizeId()).name(review.petSizeName()).build();
        BreedResponse breed = BreedResponse.builder().id(review.breedId()).name(review.breedName()).size(petSize)
                .build();
        PetProfileResponse petProfile = PetProfileResponse.builder().id(review.petId()).name(review.petName())
                .profileUrl(review.petProfileImageUrl())
                .breed(breed).writtenWeight(review.petWrittenWeight()).writtenAge(review.getPetAge()).build();
        HelpfulReactionResponse helpful = HelpfulReactionResponse.builder()
                .count(helpfulReaction.helpfulReactionCount()).reacted(helpfulReaction.reacted()).build();

        return GetReviewResponse.builder()
                .id(review.id())
                .tastePreference(review.tastePreference().getDescription())
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
