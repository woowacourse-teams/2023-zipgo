package zipgo.review.dto.response;

import java.util.List;

public record GetReviewMetadataResponse(
        List<Metadata> breedSizes,
        List<Metadata> sortBy,
        List<Metadata> ageGroups,
        List<Metadata> breeds
) {

    public record Metadata(Long id, String name) {

    }

}
