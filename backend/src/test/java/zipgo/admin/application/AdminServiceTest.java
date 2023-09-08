package zipgo.admin.application;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.admin.dto.PetFoodCreateRequest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.fixture.FunctionalityFixture;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.fixture.PrimaryIngredientFixture;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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


    @Test
    void createBrand() {
        //given
        BrandCreateRequest 무민_브랜드_생성_요청 = BrandFixture.무민_브랜드_생성_요청();

        //when
        Long brandId = adminService.createBrand(무민_브랜드_생성_요청, "imageUrl");

        //then
        assertDoesNotThrow(() -> brandRepository.getById(brandId));
    }

    @Test
    void createFunctionality() {
        //when
        Long functionalityId = adminService.createFunctionality(FunctionalityFixture.다이어트_기능성_요청);

        //then
        assertThat(functionalityRepository.getById(functionalityId)).isNotNull();
    }

    @Test
    void createPrimaryIngredient() {
        //when
        Long primaryIngredientId = adminService.createPrimaryIngredient(PrimaryIngredientFixture.닭고기_주원료_요청);

        //then
        assertThat(primaryIngredientRepository.getById(primaryIngredientId)).isNotNull();
    }

    @Test
    void createPetFood() {
        //given
        Brand brand = brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());
        PrimaryIngredient 돼지고기 = primaryIngredientRepository.save(PrimaryIngredientFixture.주원료_돼지고기());
        PrimaryIngredient 말미잘 = primaryIngredientRepository.save(PrimaryIngredientFixture.주원료_말미잘());
        Functionality 다이어트 = functionalityRepository.save(FunctionalityFixture.기능성_다이어트());
        Functionality 튼튼 = functionalityRepository.save(FunctionalityFixture.기능성_튼튼());
        PetFoodCreateRequest 식품_생성_요청 = PetFoodFixture.식품_생성_요청(brand.getId(), List.of(다이어트.getId(), 튼튼.getId()),
                List.of(돼지고기.getId(), 말미잘.getId()));

        //when
        Long petFoodId = adminService.createPetFood(식품_생성_요청, "imageUrl");

        //then
        PetFood byId = petFoodRepository.getById(petFoodId);
        Assertions.assertDoesNotThrow(() -> byId);
    }

}