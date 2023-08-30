package zipgo.petfood.infra.persist;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.PetFoodEffect;
import zipgo.petfood.domain.repository.PetFoodRepository;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.오리젠_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.퓨리나_식품_브랜드_생성;
import static zipgo.petfood.domain.fixture.PetFoodEffectFixture.기능성_다이어트;
import static zipgo.petfood.domain.fixture.PetFoodEffectFixture.기능성_짱짱;
import static zipgo.petfood.domain.fixture.PetFoodEffectFixture.기능성_튼튼;
import static zipgo.petfood.domain.fixture.PetFoodEffectFixture.원재료_닭고기;
import static zipgo.petfood.domain.fixture.PetFoodEffectFixture.원재료_돼지고기;
import static zipgo.petfood.domain.fixture.PetFoodEffectFixture.원재료_소고기;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.미국_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.유럽_영양기준_만족_식품;
import static zipgo.petfood.domain.type.PetFoodOption.FUNCTIONALITY;
import static zipgo.petfood.domain.type.PetFoodOption.PRIMARY_INGREDIENT;

@Transactional
@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(properties = {"spring.sql.init.mode=never"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PetFoodQueryRepositoryTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private PetFoodQueryRepositoryImpl petFoodQueryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        Brand 아카나 = brandRepository.save(아카나_식품_브랜드_생성());
        Brand 오리젠 = brandRepository.save(오리젠_식품_브랜드_생성());
        Brand 퓨리나 = brandRepository.save(퓨리나_식품_브랜드_생성());

        PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(아카나, List.of(기능성_튼튼(), 원재료_소고기()));
        PetFood 미국_영양기준_만족_식품 = 미국_영양기준_만족_식품(오리젠, List.of(기능성_짱짱(), 원재료_돼지고기()));
        PetFood 유럽_영양기준_만족_식품 = 유럽_영양기준_만족_식품(퓨리나, List.of(기능성_다이어트(), 원재료_닭고기()));

        petFoodRepository.save(모든_영양기준_만족_식품);
        petFoodRepository.save(미국_영양기준_만족_식품);
        petFoodRepository.save(유럽_영양기준_만족_식품);
    }

    @Test
    void 식품_효과가_여러개일때_모든_조건에_맞는_식품을_필터링한다() {
        // given
        List<PetFood> allFoods = petFoodRepository.findAll();
        Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId();

        List<String> brandsName = List.of("오리젠");
        List<String> standards = List.of("미국");
        List<String> primaryIngredientList = List.of("튼튼", "짱짱");
        List<String> functionalityList = List.of("돼지고기");

        // when
        List<PetFood> petFoods = petFoodQueryRepository.findPagingPetFoods(
                brandsName,
                standards,
                getConcatList(functionalityList, primaryIngredientList),
                lastPetFoodId,
                20
        );

        // then
        assertAll(
                () -> assertThat(petFoods).hasSize(1),
                () -> assertThat(petFoods).extracting(PetFood::getName)
                        .contains("미국 영양기준 만족 식품"),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                        .contains("오리젠")
        );
    }

    @Test
    void 모든_조건에_맞는_식품을_필터링한다() {
        // given
        List<PetFood> allFoods = petFoodRepository.findAll();
        Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId();

        List<String> brandsName = List.of("오리젠");
        List<String> standards = List.of("미국");
        List<String> primaryIngredientList = List.of("짱짱", "튼튼");
        List<String> functionalityList = List.of("돼지고기");

        // when
        List<PetFood> petFoods = petFoodQueryRepository.findPagingPetFoods(
                brandsName,
                standards,
                getConcatList(functionalityList, primaryIngredientList),
                lastPetFoodId,
                20
        );

        // then
        assertAll(
                () -> assertThat(petFoods).hasSize(1),
                () -> assertThat(petFoods).extracting(PetFood::getName)
                        .contains("미국 영양기준 만족 식품"),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                        .contains("오리젠")
        );
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
        List<PetFood> petFoods = petFoodQueryRepository.findPagingPetFoods(
                brandsName,
                standards,
                getConcatList(functionalityList, primaryIngredientList),
                lastPetFoodId,
                20
        );

        // then
        assertAll(
                () -> assertThat(petFoods).hasSize(1),
                () -> assertThat(petFoods).extracting(PetFood::getName)
                        .contains("미국 영양기준 만족 식품"),
                () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                        .contains("오리젠")
        );
    }

    @Test
    void 사료_아이디와_일치하는_사료를_조회한다() {
        // given
        List<PetFood> allFoods = petFoodRepository.findAll();
        Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId();

        // when
        PetFood petFood = petFoodQueryRepository.findPetFoodWithReviewsByPetFoodId(lastPetFoodId);

        // then
        assertThat(petFood.getId()).isEqualTo(lastPetFoodId);
    }

    @Test
    void 식품_기능성을_조회한다() {
        // when
        List<PetFoodEffect> petFoodEffectsBy = petFoodQueryRepository.findPetFoodEffectsBy(FUNCTIONALITY);

        // then
        for (PetFoodEffect petFoodEffect : petFoodEffectsBy) {
            assertThat(petFoodEffect.getPetFoodOption())
                    .isEqualTo(FUNCTIONALITY);
        }
    }

    @Test
    void 식품_주원료을_조회한다() {
        // when
        List<PetFoodEffect> petFoodEffectsBy = petFoodQueryRepository.findPetFoodEffectsBy(PRIMARY_INGREDIENT);

        // then
        for (PetFoodEffect petFoodEffect : petFoodEffectsBy) {
            assertThat(petFoodEffect.getPetFoodOption())
                    .isEqualTo(PRIMARY_INGREDIENT);
        }
    }

    private static List<String> getConcatList(List<String> firstList, List<String> secondList) {
        return Stream.concat(
                        firstList.stream(),
                        secondList.stream())
                .toList();
    }

}
