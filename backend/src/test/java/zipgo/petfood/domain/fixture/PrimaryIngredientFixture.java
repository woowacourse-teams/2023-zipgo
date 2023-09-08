package zipgo.petfood.domain.fixture;

import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.admin.dto.PrimaryIngredientCreateRequest;

import static zipgo.petfood.domain.PrimaryIngredient.builder;

public class PrimaryIngredientFixture {

    public static PrimaryIngredientCreateRequest 닭고기_주원료_요청 = new PrimaryIngredientCreateRequest("닭고기");

    public static PrimaryIngredient 주원료_닭고기() {
        PrimaryIngredient 주원료_닭고기 = builder()
                .name("닭고기")
                .build();
        return 주원료_닭고기;
    }

    public static PrimaryIngredient 주원료_소고기() {
        PrimaryIngredient 주원료_소고기 = builder()
                .name("소고기")
                .build();
        return 주원료_소고기;
    }

    public static PrimaryIngredient 주원료_돼지고기() {
        PrimaryIngredient 주원료_돼지고기 = builder()
                .name("돼지고기")
                .build();
        return 주원료_돼지고기;
    }

    public static PrimaryIngredient 주원료_말미잘() {
        PrimaryIngredient 주원료_말미잘 = builder()
                .name("말미잘")
                .build();
        return 주원료_말미잘;
    }

}
