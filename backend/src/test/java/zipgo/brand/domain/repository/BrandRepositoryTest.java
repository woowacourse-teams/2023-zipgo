package zipgo.brand.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.common.repository.RepositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

class BrandRepositoryTest extends RepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void getById() {
        //given
        Brand brand = brandRepository.save(BrandFixture.오리젠_식품_브랜드_생성());

        //when
        Brand findBrand = brandRepository.getById(brand.getId());

        //then
        assertThat(findBrand).isEqualTo(brand);
    }

}
