package zipgo.petfood.domain.repository;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import zipgo.common.config.QueryDslTestConfig;
import zipgo.petfood.domain.PetFood;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslTestConfig.class)
@Sql(scripts = {"classpath:truncate.sql", "classpath:data.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PetFoodQueryRepositoryTest {

    @Autowired
    private PetFoodQueryRepository petFoodQueryRepository;

    @Test
    void 조건에_맞는_식품을_필터링한다() {
        // given
        List<String> brandsName = List.of("오리젠");
        List<String> standards = List.of("유럽");
        List<String> primaryIngredientList = EMPTY_LIST;
        List<String> functionalityList = EMPTY_LIST;

        // when
        List<PetFood> petFoods = petFoodQueryRepository.findPetFoods(brandsName, standards, primaryIngredientList,
                functionalityList);

        // then
        Assertions.assertAll(
                () -> assertThat(petFoods).hasSize(1),
                () -> assertThat(petFoods).extracting(PetFood::getName)
                        .contains("[고집] 돌아온 배배"),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                        .contains("오리젠")
        );
    }

}
