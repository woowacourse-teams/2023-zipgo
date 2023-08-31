package zipgo.brand.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.QueryServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class BrandQueryServiceTest extends QueryServiceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandQueryService brandQueryService;

    @Test
    void getBrands() {
        //given
        brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());
        brandRepository.save(BrandFixture.인스팅트_식품_브랜드_생성());

        //when, then
        assertThat(brandQueryService.getBrands()).hasSize(2);
    }

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
