package zipgo.admin.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.QueryServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

class AdminQueryServiceTest extends QueryServiceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private AdminQueryService brandQueryService;

    @Test
    void getBrands() {
        //given
        brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());
        brandRepository.save(BrandFixture.인스팅트_식품_브랜드_생성());

        //when, then
        assertThat(brandQueryService.getBrands()).hasSize(2);
    }

}