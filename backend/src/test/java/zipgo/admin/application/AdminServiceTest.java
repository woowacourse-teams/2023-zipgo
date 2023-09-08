package zipgo.admin.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.fixture.FunctionalityFixture;
import zipgo.petfood.domain.repository.FunctionalityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AdminServiceTest extends ServiceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private FunctionalityRepository functionalityRepository;

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

}