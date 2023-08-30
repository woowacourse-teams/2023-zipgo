package zipgo.petfood.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.petfood.domain.type.PetFoodOption;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PetFoodEffect that = (PetFoodEffect) o;
        return petFoodOption == that.petFoodOption && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petFoodOption, description);
    }

}
