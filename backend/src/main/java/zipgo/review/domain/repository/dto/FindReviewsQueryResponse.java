package zipgo.review.domain.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.time.Year;
import zipgo.review.domain.type.StoolCondition;
import zipgo.review.domain.type.TastePreference;

public record FindReviewsQueryResponse(
        Long id,
        Long writerId,
        int rating,
        LocalDateTime date,
        String comment,
        TastePreference tastePreference,
        StoolCondition stoolCondition,
        Long petId,
        String petName,
        String petProfileImageUrl,
        Year petBirthYear,
        double petWrittenWeight,
        Long breedId,
        String breedName,
        Long petSizeId,
        String petSizeName

) {

    @QueryProjection
    public FindReviewsQueryResponse {
    }

    public int getPetAge() {
        return date.getYear() - petBirthYear.getValue();
    }

}
