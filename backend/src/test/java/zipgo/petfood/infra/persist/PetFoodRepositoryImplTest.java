package zipgo.petfood.infra.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import zipgo.common.config.QueryDslTestConfig;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodQueryRepository;

@DataJpaTest
@Import(QueryDslTestConfig.class)
@Sql(scripts = {"classpath:truncate.sql", "classpath:data.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PetFoodRepositoryImplTest {

    @Autowired
    private PetFoodQueryRepository petFoodQueryRepository;

    @Test
    void 키워드와_브랜드_이름으로_동적_조회한다() {
        // given
        final String keyword = "diet";
        final String brand = "오리젠";
        final String primaryIngredients = "말미잘";

        // when
        List<PetFood> petFoods = petFoodQueryRepository.findPetFoods(keyword, brand, primaryIngredients);

        // then
        assertAll(
                () -> assertThat(petFoods).extracting(petFood -> petFood.getKeyword().getName()).contains(keyword),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName()).contains(brand),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getPrimaryIngredients()).contains(List.of(primaryIngredients)),
                () -> assertThat(petFoods).hasSize(1)
        );
    }

}
