package zipgo.admin.application;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.admin.dto.PetFoodCreateRequest;
import zipgo.admin.dto.PetFoodUpdateRequest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.exception.PetFoodNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static zipgo.brand.domain.fixture.BrandFixture.무민_브랜드_생성_요청;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.오리젠_식품_브랜드_생성;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_다이어트;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_튼튼;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.다이어트_기능성_요청;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.식품_생성_요청;
import static zipgo.petfood.domain.fixture.PetFoodFunctionalityFixture.식품_기능성_연관관계_매핑;
import static zipgo.petfood.domain.fixture.PetFoodPrimaryIngredientFixture.식품_주원료_연관관계_매핑;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.닭고기_주원료_요청;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_돼지고기;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_말미잘;

class AdminServiceTest extends ServiceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PrimaryIngredientRepository primaryIngredientRepository;

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private AdminService adminService;

    private Brand brand;
    private PetFood petFood;

    @BeforeEach
    void setUp() {
        Brand 오리젠_식품_브랜드 = 오리젠_식품_브랜드_생성();

        Functionality 기능성_다이어트 = 기능성_다이어트();
        PrimaryIngredient 주원료_말미잘 = 주원료_말미잘();
        PrimaryIngredient 주원료_돼지고기 = 주원료_돼지고기();
        PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(오리젠_식품_브랜드);

        식품_기능성_연관관계_매핑(모든_영양기준_만족_식품, 기능성_다이어트);
        식품_주원료_연관관계_매핑(모든_영양기준_만족_식품, 주원료_말미잘);
        식품_주원료_연관관계_매핑(모든_영양기준_만족_식품, 주원료_돼지고기);

        brandRepository.save(오리젠_식품_브랜드);
        functionalityRepository.save(기능성_다이어트);
        primaryIngredientRepository.save(주원료_말미잘);
        primaryIngredientRepository.save(주원료_돼지고기);
        brand = 오리젠_식품_브랜드;
        petFood = petFoodRepository.save(모든_영양기준_만족_식품);
    }

    @Test
    void createBrand() {
        //given
        String 식품_이미지_경로 = "imageUrl";
        BrandCreateRequest 무민_브랜드_생성_요청 = 무민_브랜드_생성_요청();

        //when
        Long brandId = adminService.createBrand(무민_브랜드_생성_요청, 식품_이미지_경로);

        //then
        assertDoesNotThrow(() -> brandRepository.getById(brandId));
    }

    @Test
    void createFunctionality() {
        //when
        Long functionalityId = adminService.createFunctionality(다이어트_기능성_요청);

        //then
        assertThat(functionalityRepository.getById(functionalityId)).isNotNull();
    }

    @Test
    void createPrimaryIngredient() {
        //when
        Long primaryIngredientId = adminService.createPrimaryIngredient(닭고기_주원료_요청);

        //then
        assertThat(primaryIngredientRepository.getById(primaryIngredientId)).isNotNull();
    }

    @Test
    void createPetFood() {
        //given
        Brand brand = brandRepository.save(아카나_식품_브랜드_생성());
        PrimaryIngredient 돼지고기 = primaryIngredientRepository.save(주원료_돼지고기());
        PrimaryIngredient 말미잘 = primaryIngredientRepository.save(주원료_말미잘());
        Functionality 다이어트 = functionalityRepository.save(기능성_다이어트());
        Functionality 튼튼 = functionalityRepository.save(기능성_튼튼());
        PetFoodCreateRequest 식품_생성_요청 = 식품_생성_요청(brand.getId(), List.of(다이어트.getId(), 튼튼.getId()),
                List.of(돼지고기.getId(), 말미잘.getId()));

        //when
        Long petFoodId = adminService.createPetFood(식품_생성_요청, "imageUrl");

        //then
        assertDoesNotThrow(() -> petFoodRepository.getById(petFoodId));
    }

    @Test
    void 식품을_수정한다() {
        // given
        PetFoodUpdateRequest request = new PetFoodUpdateRequest(
                petFood.getName(),
                brand.getName(),
                false,
                false,
                "변경된 이미지 URL",
                List.of("다이어트"),
                List.of("말미잘")
        );

        // when
        adminService.updatePetFood(petFood.getId(), request);

        // then
        assertAll(
                () -> assertThat(petFood.getHasStandard().getEurope()).isFalse(),
                () -> assertThat(petFood.getHasStandard().getUnitedStates()).isFalse(),
                () -> assertThat(petFood.getImageUrl()).isEqualTo("변경된 이미지 URL"),
                () -> assertThat(petFood.getPetFoodFunctionalities().stream()
                        .map(petFoodFunctionality -> petFoodFunctionality.getFunctionality().getName())
                        .toList()
                ).contains("다이어트"),
                () -> assertThat(petFood.getPetFoodPrimaryIngredients()).hasSize(1),
                () -> assertThat(petFood.getPetFoodPrimaryIngredients().stream()
                        .map(petFoodPrimaryIngredient -> petFoodPrimaryIngredient.getPrimaryIngredient().getName())
                        .toList()
                ).contains("말미잘")
        );
    }

    @Test
    void 식품을_빈_문자열로_수정할_시_데이터를_삭제한다() {
        // given
        PetFoodUpdateRequest request = new PetFoodUpdateRequest(
                petFood.getName(),
                brand.getName(),
                false,
                false,
                "변경된 이미지 URL",
                List.of(""),
                List.of("")
        );

        // when
        adminService.updatePetFood(petFood.getId(), request);

        // then
        assertAll(
                () -> assertThat(petFood.getHasStandard().getEurope()).isFalse(),
                () -> assertThat(petFood.getHasStandard().getUnitedStates()).isFalse(),
                () -> assertThat(petFood.getImageUrl()).isEqualTo("변경된 이미지 URL"),
                () -> assertThat(petFood.getPetFoodFunctionalities()).isEmpty(),
                () -> assertThat(petFood.getPetFoodPrimaryIngredients()).isEmpty()
        );
    }

    @Test
    void 식품을_삭제한다() {
        // when
        adminService.deletePetFood(petFood.getId());

        // then
        assertThrows(
                PetFoodNotFoundException.class,
                () -> petFoodRepository.getById(petFood.getId())
        );
    }

}
