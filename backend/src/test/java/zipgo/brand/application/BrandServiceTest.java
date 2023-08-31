package zipgo.brand.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.brand.dto.BrandCreateRequest;
import zipgo.common.service.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BrandServiceTest extends ServiceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;

    @Test
    void createBrand() {
        //given
        BrandCreateRequest 무민_브랜드_생성_요청 = BrandFixture.무민_브랜드_생성_요청();

        //when
        Long brandId = brandService.createBrand(무민_브랜드_생성_요청, "imageUrl");

        //then
        assertDoesNotThrow(() -> brandRepository.getById(brandId));
    }

}
