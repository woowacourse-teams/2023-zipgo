package zipgo.petfood.domain.repository;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.config.QueryDslTestConfig;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.fixture.FunctionalityFixture;
import zipgo.petfood.domain.fixture.PetFoodFixture;
import zipgo.petfood.domain.fixture.PrimaryIngredientFixture;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslTestConfig.class)
class PetFoodQueryRepositoryTest {

    @Autowired
    private PetFoodQueryRepository petFoodQueryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private PrimaryIngredientRepository primaryIngredientRepository;

    @Autowired
    private FunctionalityRepository functionalityRepository;


    @BeforeEach
    void setUp() {
        Brand 오리젠 = Brand.builder().name("오리젠").nation("캐나다").build();
        brandRepository.save(오리젠);
        PetFood 오리젠_돌아온_배배 = PetFoodFixture.모든_영양기준_만족_식품(오리젠);
        petFoodRepository.save(오리젠_돌아온_배배);
        primaryIngredientRepository.save(PrimaryIngredientFixture.원재료_닭고기_식품(오리젠_돌아온_배배));
        functionalityRepository.save(FunctionalityFixture.기능성_튼튼(오리젠_돌아온_배배));
    }

    @Test
    void 조건에_맞는_식품을_필터링한다() {
        // given
        List<String> brandsName = List.of("오리젠");
        List<String> standards = List.of("유럽");
        List<String> primaryIngredientList = EMPTY_LIST;
        List<String> functionalityList = EMPTY_LIST;

        // when
        List<PetFood> petFoods = petFoodQueryRepository.findPagingPetFoods(brandsName, standards, primaryIngredientList,
                functionalityList, null, 20);

        // then
        Assertions.assertAll(
                () -> assertThat(petFoods).hasSize(1),
                () -> assertThat(petFoods).extracting(PetFood::getName)
                        .contains("모든 영양기준 만족 식품"),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                        .contains("오리젠")
        );
    }

}
