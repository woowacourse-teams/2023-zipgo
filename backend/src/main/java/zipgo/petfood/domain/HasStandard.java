package zipgo.petfood.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class HasStandard {

    @Builder.Default
    @Column(nullable = false, name = "us_standard")
    private Boolean unitedStates = true;

    @Builder.Default

    @Column(nullable = false, name = "eu_standard")
    private Boolean europe = true;

}
