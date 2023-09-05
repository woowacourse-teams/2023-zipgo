package zipgo.petfood.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.domain.fixture.PrimaryIngredientFixture;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

import static org.assertj.core.api.Assertions.assertThat;

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

}

