package zipgo.petfood.application;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.fixture.PrimaryIngredientFixture;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.presentation.dto.PrimaryIngredientSelectResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PrimaryIngredientServiceTest extends ServiceTest {

    @Autowired
    private PrimaryIngredientRepository primaryIngredientRepository;

    @Autowired
    private PrimaryIngredientService primaryIngredientService;
    @Test
    void createFunctionality() {
        //when
        Long primaryIngredientId = primaryIngredientService.createPrimaryIngredient(PrimaryIngredientFixture.닭고기_주원료_요청);

        //then
        assertThat(primaryIngredientRepository.getById(primaryIngredientId)).isNotNull();
    }

    @Test
    void getPrimaryIngredients() {
        //given
        primaryIngredientRepository.save(PrimaryIngredientFixture.주원료_닭고기());
        primaryIngredientRepository.save(PrimaryIngredientFixture.주원료_말미잘());

        //when
        List<PrimaryIngredientSelectResponse> primaryIngredients = primaryIngredientService.getPrimaryIngredients();
        PrimaryIngredientSelectResponse 닭고기_response = primaryIngredients.get(0);
        PrimaryIngredientSelectResponse 말미잘_response = primaryIngredients.get(1);
        //then
        assertAll(
                () -> assertThat(primaryIngredients.size()).isEqualTo(2),
                () -> assertThat(닭고기_response.name()).isEqualTo("닭고기"),
                () -> assertThat(말미잘_response.name()).isEqualTo("말미잘")
        );
    }

}

