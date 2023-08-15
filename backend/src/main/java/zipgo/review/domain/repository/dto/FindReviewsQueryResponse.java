package zipgo.review.domain.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import zipgo.review.domain.AdverseReaction;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;

public record FindReviewsQueryResponse(
        Long id,
        int rating,
        LocalDateTime date,
        String comment,
        TastePreference tastePreference,
        StoolCondition stoolCondition,
        List<AdverseReaction> adverseReactions,
        Long petId,
        String petName,
        Year petBirthYear,
        double petWrittenWeight,
        Long breedId, String breedName,
        Long petSizeId, String petSizeName

) {

    @QueryProjection
    public FindReviewsQueryResponse {
    }

    public int getPetAge() {
        return date.getYear() - petBirthYear.getValue();
    }

}
