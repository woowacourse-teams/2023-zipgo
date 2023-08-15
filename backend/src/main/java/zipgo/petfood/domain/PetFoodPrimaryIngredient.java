package zipgo.petfood.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class PetFoodPrimaryIngredient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pet_food_id")
    private PetFood petFood;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "primary_ingredient_id")
    private PrimaryIngredient primaryIngredient;

    public void changeRelations(PetFood petFood, PrimaryIngredient primaryIngredient) {
        this.petFood = petFood;
        this.petFood.addPetFoodPrimaryIngredient(this);

        this.primaryIngredient = primaryIngredient;
        this.primaryIngredient.addPetFoodPrimaryIngredient(this);
    }

}
