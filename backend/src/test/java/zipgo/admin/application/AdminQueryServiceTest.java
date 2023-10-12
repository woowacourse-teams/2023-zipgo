package zipgo.admin.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.admin.dto.FunctionalitySelectResponse;
import zipgo.admin.dto.PetFoodReadResponse;
import zipgo.admin.dto.PrimaryIngredientSelectResponse;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.fixture.FunctionalityFixture;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.fixture.PrimaryIngredientFixture;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AdminQueryServiceTest extends ServiceTest {

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Autowired
    private PrimaryIngredientRepository primaryIngredientRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private AdminQueryService adminQueryService;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Test
    void getBrands() {
        //given
        brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());
        brandRepository.save(BrandFixture.인스팅트_식품_브랜드_생성());

        //when, then
        assertThat(adminQueryService.getBrands()).hasSize(2);
    }

    @Test
    void getFunctionalities() {
        //given
        functionalityRepository.save(FunctionalityFixture.기능성_튼튼());
        functionalityRepository.save(FunctionalityFixture.기능성_짱짱());

        //when
        List<FunctionalitySelectResponse> functionalities = adminQueryService.getFunctionalities();
        FunctionalitySelectResponse 튼튼_response = functionalities.get(0);
        FunctionalitySelectResponse 짱짱_response = functionalities.get(1);
        //then
        assertAll(
                () -> assertThat(functionalities.size()).isEqualTo(2),
                () -> assertThat(튼튼_response.name()).isEqualTo("튼튼"),
                () -> assertThat(짱짱_response.name()).isEqualTo("짱짱")
        );
    }

    @Test
    void getPrimaryIngredients() {
        //given
        primaryIngredientRepository.save(PrimaryIngredientFixture.주원료_닭고기());
        primaryIngredientRepository.save(PrimaryIngredientFixture.주원료_말미잘());

        //when
        List<PrimaryIngredientSelectResponse> primaryIngredients = adminQueryService.getPrimaryIngredients();
        PrimaryIngredientSelectResponse 닭고기_response = primaryIngredients.get(0);
        PrimaryIngredientSelectResponse 말미잘_response = primaryIngredients.get(1);

        //then
        assertAll(
                () -> assertThat(primaryIngredients.size()).isEqualTo(2),
                () -> assertThat(닭고기_response.name()).isEqualTo("닭고기"),
                () -> assertThat(말미잘_response.name()).isEqualTo("말미잘")
        );
    }

    @Test
    void 식품을_아이디로_조회한다() {
        // given
        Brand 아카나 = BrandFixture.아카나_식품_브랜드_생성();
        brandRepository.save(아카나);
        PetFood 식품 = PetFoodFixture.모든_영양기준_만족_식품(아카나);
        petFoodRepository.save(식품);

        // when
        PetFoodReadResponse response = adminQueryService.getPetFoodById(식품.getId());

        // then
        assertThat(response.id()).isEqualTo(식품.getId());
    }

}
