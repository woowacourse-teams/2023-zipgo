package zipgo.petfood.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipgo.common.entity.BaseTimeEntity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PrimaryIngredient extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "primaryIngredient")
    private List<PetFoodPrimaryIngredient> petFoodPrimaryIngredients = new ArrayList<>();

    public void addPetFoodPrimaryIngredient(PetFoodPrimaryIngredient petFoodPrimaryIngredient) {
        petFoodPrimaryIngredients.add(petFoodPrimaryIngredient);
    }

}
