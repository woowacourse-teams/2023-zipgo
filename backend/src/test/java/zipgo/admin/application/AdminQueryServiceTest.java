package zipgo.admin.application;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.QueryServiceTest;
import zipgo.petfood.domain.fixture.FunctionalityFixture;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.admin.dto.FunctionalitySelectResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AdminQueryServiceTest extends QueryServiceTest {

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private AdminQueryService adminQueryService;

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

}