package zipgo.petfood.application;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.QueryServiceTest;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.brand.domain.fixture.BrandFixture.아카나;
import static zipgo.brand.domain.fixture.BrandFixture.오리젠;
import static zipgo.brand.domain.fixture.BrandFixture.퓨리나;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_다이어트;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_짱짱;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_튼튼;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.미국_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.유럽_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.원재료_닭고기_식품;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.원재료_돼지고기_식품;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.원재료_소고기_식품;

class PetFoodQueryServiceTest extends QueryServiceTest {

    @Autowired
    private PetFoodQueryService petFoodQueryService;

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
        Brand 아카나 = brandRepository.save(아카나());
        Brand 오리젠 = brandRepository.save(오리젠());
        Brand 퓨리나 = brandRepository.save(퓨리나());

        PetFood 모든_영양기준_만족_식품 = petFoodRepository.save(모든_영양기준_만족_식품(아카나));
        PetFood 미국_영양기준_만족_식품 = petFoodRepository.save(미국_영양기준_만족_식품(오리젠));
        PetFood 유럽_영양기준_만족_식품 = petFoodRepository.save(유럽_영양기준_만족_식품(퓨리나));

        primaryIngredientRepository.save(원재료_소고기_식품(모든_영양기준_만족_식품));
        primaryIngredientRepository.save(원재료_돼지고기_식품(미국_영양기준_만족_식품));
        primaryIngredientRepository.save(원재료_닭고기_식품(유럽_영양기준_만족_식품));

        functionalityRepository.save(기능성_튼튼(모든_영양기준_만족_식품));
        functionalityRepository.save(기능성_짱짱(미국_영양기준_만족_식품));
        functionalityRepository.save(기능성_다이어트(유럽_영양기준_만족_식품));
    }

    @Nested
    @Transactional
    class 필터_조회 {

        @Test
        void 브랜드를_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    List.of("퓨리나"),
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).hasSize(1),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                            .isEqualTo(List.of("퓨리나"))
            );
        }

//        @Test
//        void 영양_기준을_만족하는_식품만_반환한다() {
//            // when
//            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
//                    EMPTY_LIST,
//                    List.of("유럽"),
//                    EMPTY_LIST,
//                    EMPTY_LIST
//            );
//
//            // then
//            assertThat(petFoods).hasSize(2);
//        }

        @Test
        void 주원료를_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    EMPTY_LIST,
                    EMPTY_LIST,
                    List.of("소고기"),
                    EMPTY_LIST
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).hasSize(1),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getPrimaryIngredients().get(0).getName())
                            .isEqualTo(List.of("소고기"))
            );
        }

        @Test
        void 기능성을_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST,
                    List.of("튼튼")
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).hasSize(1),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getFunctionalities().get(0).getName())
                            .isEqualTo(List.of("튼튼"))
            );
        }

        @Test
        void 모든_필터를_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    List.of("아카나", "오리젠"),
                    List.of("미국"),
                    List.of("소고기"),
                    List.of("튼튼")
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).hasSize(1),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getHasStandard().getEurope())
                            .contains(true),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getPrimaryIngredients().get(0).getName())
                            .isEqualTo(List.of("소고기")),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getFunctionalities().get(0).getName())
                            .isEqualTo(List.of("튼튼"))
            );
        }

        @Test
        void 모든_정보가_NULL일_경우_모든_식품을_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST
            );

            // then
            assertThat(petFoods.size()).isEqualTo(petFoodRepository.findAll().size());
        }

    }

    @Test
    void 아이디로_식품을_조회할_수_있다() {
        // given
        Brand 브랜드 = brandRepository.findAll().get(0);
        PetFood 테스트용_식품 = 모든_영양기준_만족_식품(브랜드);
        Long 아이디 = petFoodRepository.save(테스트용_식품).getId();

        // when
        PetFood 조회된_식품 = petFoodQueryService.getPetFoodBy(아이디);

        // then
        assertThat(조회된_식품).isEqualTo(테스트용_식품);
    }

//    @Test
////    @Disabled("230802 메타데이터 API 요구사항 변경사항 반영 전 비활성화")
//    void 필터링에_필요한_식품_데이터를_조회한다() {
//        // given
//        Keyword keyword = keywordRepository.save(new Keyword(1L, "diet"));
//        Brand brand = brandRepository.save(식품_브랜드_생성하기());
//        petFoodRepository.save(키워드_있는_식품_초기화(keyword, brand));
//
//        // when
//        FilterResponse metadata = petFoodQueryService.getMetadataForFilter();
//
//        // then
////        assertAll(
////                () -> assertThat(metadata.brands())
////                        .extracting(BrandResponse::brandName).contains("오리젠", "퓨리나"),
////                () -> assertThat(metadata.functionalities())
////                        .contains("튼튼", "짱짱"),
////                () -> assertThat(metadata.mainIngredients())
////                        .contains("닭고기", "쌀", "말미잘"),
////                () -> assertThat(metadata.nutritionStandards())
////                        .contains("유럽", "미국")
////        );
//    }

}
