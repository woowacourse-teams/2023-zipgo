package zipgo.petfood.infra.persist;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.오리젠_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.퓨리나_식품_브랜드_생성;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_다이어트;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_짱짱;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_튼튼;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.미국_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.유럽_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFunctionalityFixture.식품_기능성_연관관계_매핑;
import static zipgo.petfood.domain.fixture.PetFoodIngredientFixture.식품_주원료_연관관계_매핑;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_닭고기;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_돼지고기;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_소고기;

@Transactional
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PetFoodQueryRepositoryTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private PetFoodQueryRepository petFoodQueryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Autowired
    private PrimaryIngredientRepository primaryIngredientRepository;

    @BeforeEach
    void setUp() {
        Brand 아카나 = brandRepository.save(아카나_식품_브랜드_생성());
        Brand 오리젠 = brandRepository.save(오리젠_식품_브랜드_생성());
        Brand 퓨리나 = brandRepository.save(퓨리나_식품_브랜드_생성());

        PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(아카나);
        PetFood 미국_영양기준_만족_식품 = 미국_영양기준_만족_식품(오리젠);
        PetFood 유럽_영양기준_만족_식품 = 유럽_영양기준_만족_식품(퓨리나);

        Functionality 기능성_튼튼 = 기능성_튼튼();
        Functionality 기능성_짱짱 = 기능성_짱짱();
        Functionality 기능성_다이어트 = 기능성_다이어트();

        식품_기능성_연관관계_매핑(모든_영양기준_만족_식품, 기능성_튼튼);
        식품_기능성_연관관계_매핑(미국_영양기준_만족_식품, 기능성_짱짱);
        식품_기능성_연관관계_매핑(유럽_영양기준_만족_식품, 기능성_다이어트);

        PrimaryIngredient 원재료_소고기 = 주원료_소고기();
        PrimaryIngredient 원재료_돼지고기 = 주원료_돼지고기();
        PrimaryIngredient 원재료_닭고기 = 주원료_닭고기();

        식품_주원료_연관관계_매핑(모든_영양기준_만족_식품, 원재료_소고기);
        식품_주원료_연관관계_매핑(미국_영양기준_만족_식품, 원재료_돼지고기);
        식품_주원료_연관관계_매핑(유럽_영양기준_만족_식품, 원재료_닭고기);

        petFoodRepository.save(모든_영양기준_만족_식품);
        petFoodRepository.save(미국_영양기준_만족_식품);
        petFoodRepository.save(유럽_영양기준_만족_식품);

        functionalityRepository.save(기능성_튼튼);
        functionalityRepository.save(기능성_짱짱);
        functionalityRepository.save(기능성_다이어트);

        primaryIngredientRepository.save(원재료_소고기);
        primaryIngredientRepository.save(원재료_돼지고기);
        primaryIngredientRepository.save(원재료_닭고기);
    }

    @Test
    void 조건에_맞는_식품을_필터링한다() {
        // given
        List<PetFood> allFoods = petFoodRepository.findAll();
        Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId();

        List<String> brandsName = List.of("오리젠");
        List<String> standards = List.of("미국");
        List<String> primaryIngredientList = EMPTY_LIST;
        List<String> functionalityList = EMPTY_LIST;

        // when
        List<PetFood> petFoods = petFoodQueryRepository.findPagingPetFoods(brandsName, standards, primaryIngredientList,
                functionalityList, lastPetFoodId, 20);

        // then
        Assertions.assertAll(
                () -> assertThat(petFoods).hasSize(1),
                () -> assertThat(petFoods).extracting(PetFood::getName)
                        .contains("미국 영양기준 만족 식품"),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                        .contains("오리젠")
        );
    }

}
