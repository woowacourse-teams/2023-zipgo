package zipgo.petfood.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.presentation.dto.PetFoodCreateRequest;

class PetFoodServiceTest extends ServiceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private PetFoodService petFoodService;

    @Test
    void createPetFood() {
        //given
        Brand brand = brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());
        PetFoodCreateRequest 식품_생성_요청 = PetFoodFixture.식품_생성_요청(brand.getId());

        //when
        Long petFoodId = petFoodService.createPetFood(식품_생성_요청, "imageUrl");

        //then
        Assertions.assertDoesNotThrow(() -> petFoodRepository.getById(petFoodId));
    }

}
