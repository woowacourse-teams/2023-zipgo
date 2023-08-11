package zipgo.petfood.infra.persist;

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

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        List<String> 브랜드 = List.of("오리젠");
        List<String> 영양기준 = EMPTY_LIST;
        List<String> 주원료 = List.of("닭고기");
        List<String> 기능성 = List.of("튼튼");

        // when
        List<PetFood> petFoods = petFoodQueryRepository.findPagingPetFoods(브랜드, 영양기준, 주원료, 기능성,
                5L, 20);

        // then
        assertAll(
                () -> assertThat(petFoods).hasSize(1),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getHasStandard().getUnitedStates())
                        .contains(false),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getHasStandard().getEurope())
                        .contains(true)
        );
    }

}
