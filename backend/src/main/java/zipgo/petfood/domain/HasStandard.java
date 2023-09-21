package zipgo.petfood.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class HasStandard {

    @Default
    @Column(nullable = false, name = "us_standard")
    private Boolean unitedStates = true;

    @Default
    @Column(nullable = false, name = "eu_standard")
    private Boolean europe = true;

    public void updateEuStandard(boolean euStandard) {
        this.europe = euStandard;
    }

    public void updateUsStandard(boolean usStandard) {
        this.unitedStates = usStandard;
    }

}
