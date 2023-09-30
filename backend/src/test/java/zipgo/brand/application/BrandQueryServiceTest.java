package zipgo.brand.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class BrandQueryServiceTest extends ServiceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandQueryService brandQueryService;

    @Test
    void findBrand() {
        //given
        Brand brand = brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());

        //when
        Brand findBrand = brandQueryService.findBrand(brand.getId());

        //then
        assertThat(findBrand).isEqualTo(brand);
    }

}
