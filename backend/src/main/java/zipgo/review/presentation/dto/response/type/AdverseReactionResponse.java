package zipgo.review.presentation.dto.response.type;

import zipgo.review.domain.type.AdverseReactionType;

public record AdverseReactionResponse(
        String name,
        int percentage
) {

    public static AdverseReactionResponse from(AdverseReactionType adverseReactionType, int percentage) {
        return new AdverseReactionResponse(adverseReactionType.getDescription(), percentage);
    }

}
