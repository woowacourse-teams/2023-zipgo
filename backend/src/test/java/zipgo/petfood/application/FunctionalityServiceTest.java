package zipgo.petfood.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.fixture.FunctionalityFixture;
import zipgo.petfood.domain.repository.FunctionalityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FunctionalityServiceTest extends ServiceTest {

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Autowired
    private FunctionalityService functionalityService;

    @Test
    void createFunctionality() {
        //when
        Long functionalityId = functionalityService.createFunctionality(FunctionalityFixture.다이어트_기능성_요청);

        //then
        assertThat(functionalityRepository.getById(functionalityId)).isNotNull();
    }

}

