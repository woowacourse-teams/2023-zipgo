package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredient;

import static zipgo.petfood.domain.Functionality.*;

public class FunctionalityFixture {

    public static Functionality 기능성_다이어트(PetFood petFood) {
        Functionality 다이어트 = Functionality.builder()
                .name("다이어트")
                .petFood(petFood)
                .build();
        다이어트.changePetFood(petFood);
        return 다이어트;
    }

    public static Functionality 기능성_튼튼(PetFood petFood) {
        Functionality 튼튼 = Functionality.builder()
                .name("튼튼")
                .petFood(petFood)
                .build();
        튼튼.changePetFood(petFood);
        return 튼튼;
    }

    public static Functionality 기능성_짱짱(PetFood petFood) {
        Functionality 짱짱 = Functionality.builder()
                .name("짱짱")
                .petFood(petFood)
                .build();
        짱짱.changePetFood(petFood);
        return 짱짱;
    }

}
