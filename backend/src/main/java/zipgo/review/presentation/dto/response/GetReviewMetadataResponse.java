package zipgo.review.presentation.dto.response;

import java.util.List;

public record GetReviewMetadataResponse(
        List<Metadata> petSizes,
        List<Metadata> sortBy,
        List<Metadata> ageGroups,
        List<Metadata> breeds
) {

    public record Metadata(Long id, String name) {

    }

}
