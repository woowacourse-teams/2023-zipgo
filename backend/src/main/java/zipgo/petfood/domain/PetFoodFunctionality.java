package zipgo.petfood.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class PetFoodFunctionality {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pet_food_id")
    private PetFood petFood;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "functionality_id")
    private Functionality functionality;

    public void changeRelations(PetFood petFood, Functionality functionality) {
        this.petFood = petFood;
        petFood.addPetFoodFunctionality(this);
        this.functionality = functionality;
        functionality.addPetFoodFunctionality(this);
    }

}
