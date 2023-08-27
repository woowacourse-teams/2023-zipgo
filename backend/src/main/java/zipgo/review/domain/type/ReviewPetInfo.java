package zipgo.review.domain.type;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ReviewPetInfo {

    private Double weight;

    @Enumerated(value = STRING)
    private StoolCondition stoolCondition;

    @Enumerated(value = STRING)
    private TastePreference tastePreference;

    public void changeStoolCondition(String stoolCondition) {
        this.stoolCondition = StoolCondition.from(stoolCondition);
    }

    public void changeTastePreference(String tastePreference) {
        this.tastePreference = TastePreference.from(tastePreference);
    }

}
