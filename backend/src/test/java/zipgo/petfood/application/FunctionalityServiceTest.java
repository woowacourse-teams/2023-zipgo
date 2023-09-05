package zipgo.petfood.application;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.fixture.FunctionalityFixture;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.presentation.dto.FunctionalitySelectResponse;

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

    @Test
    void getFunctionalities() {
        //given
        functionalityRepository.save(FunctionalityFixture.기능성_튼튼());
        functionalityRepository.save(FunctionalityFixture.기능성_짱짱());

        //when
        List<FunctionalitySelectResponse> functionalities = functionalityService.getFunctionalities();
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

