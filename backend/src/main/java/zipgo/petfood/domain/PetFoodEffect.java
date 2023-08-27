package zipgo.petfood.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.petfood.domain.type.PetFoodOption;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PetFoodEffect {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(value = STRING)
    private PetFoodOption petFoodOption;

    @Column(unique = true)
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pet_food_id")
    private PetFood petFood;

    public void changeRelation(PetFood petFood) {
        this.petFood = petFood;
        petFood.addPetFoodEffect(this);
    }

    public boolean isEqualTo(PetFoodOption petFoodOption) {
        return petFoodOption.equals(petFoodOption);
    }

}
