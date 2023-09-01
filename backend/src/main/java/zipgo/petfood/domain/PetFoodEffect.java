package zipgo.petfood.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.petfood.domain.type.PetFoodOption;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class PetFoodEffect {

    @Enumerated(value = STRING)
    private PetFoodOption petFoodOption;

    @Column(length = 128, nullable = false)
    private String description;

    @Builder
    public PetFoodEffect(PetFoodOption petFoodOption, String description) {
        this.petFoodOption = petFoodOption;
        this.description = description;
    }

    public boolean isEqualTo(PetFoodOption petFoodOption) {
        return this.petFoodOption.equals(petFoodOption);
    }

}
