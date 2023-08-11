package zipgo.petfood.application;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.service.ServiceTest;
import zipgo.petfood.application.dto.FilterDto;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.presentation.dto.FilterResponse;
import zipgo.petfood.presentation.dto.FilterResponse.BrandResponse;
import zipgo.petfood.presentation.dto.FilterResponse.FunctionalityResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.오리젠_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.인스팅트_식품_브랜드_생성;
import static zipgo.brand.domain.fixture.BrandFixture.퓨리나_식품_브랜드_생성;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_다이어트;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_짱짱;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_튼튼;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.미국_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.유럽_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.원재료_닭고기_식품;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.원재료_돼지고기_식품;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.원재료_소고기_식품;
import static zipgo.petfood.presentation.dto.FilterResponse.NutrientStandardResponse;
import static zipgo.petfood.presentation.dto.FilterResponse.PrimaryIngredientResponse;

class PetFoodQueryServiceTest extends ServiceTest {

    private static final int size = 20;


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
        Brand 아카나 = brandRepository.save(아카나_식품_브랜드_생성());
        Brand 오리젠 = brandRepository.save(오리젠_식품_브랜드_생성());
        Brand 퓨리나 = brandRepository.save(퓨리나_식품_브랜드_생성());

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
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();
            Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId() + 1;

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            List.of("퓨리나"),
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST
                    ),
                    lastPetFoodId,
                    size
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).hasSize(1),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getName())
                            .isEqualTo(List.of("퓨리나"))
            );
        }

        @Test
        void 브랜드를_만족하고_lastPetFoodId보다_작은_식품만_반환한다() {
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            List.of("퓨리나"),
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST
                    ),
                    allFoods.get(2).getId(),
                    size
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).hasSize(0)
            );
        }

        @Test
        void 영양_기준을_만족하는_식품만_반환한다() {
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();
            Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId() + 1;

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            EMPTY_LIST,
                            List.of("유럽"),
                            EMPTY_LIST,
                            EMPTY_LIST
                    ),
                    lastPetFoodId,
                    size
            );

            // then
            assertThat(petFoods).hasSize(2);
        }

        @Test
        void 주원료를_만족하는_식품만_반환한다() {
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();
            Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId() + 1;

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            EMPTY_LIST,
                            EMPTY_LIST,
                            List.of("소고기"),
                            EMPTY_LIST
                    ),
                    lastPetFoodId,
                    size
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
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();
            Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId() + 1;

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST,
                            List.of("튼튼")
                    ),
                    lastPetFoodId,
                    size
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
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();
            Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId() + 1;

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            List.of("아카나", "오리젠"),
                            List.of("미국"),
                            List.of("소고기"),
                            List.of("튼튼")
                    ),
                    lastPetFoodId,
                    size
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
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();
            Long lastPetFoodId = allFoods.get(allFoods.size() - 1).getId() + 1;

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST
                    ),
                    lastPetFoodId,
                    size
            );

            // then
            assertThat(petFoods.size()).isEqualTo(petFoodRepository.findAll().size());
        }

        @Test
        void lastPetFoodId_보다_작은_식품을_반환한다() {
            //given
            List<PetFood> allFoods = petFoodRepository.findAll();

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST
                    ),
                    allFoods.get(1).getId(),
                    size
            );

            // then
            assertThat(petFoods.size()).isEqualTo(1);
        }

        @Test
        void 처음_조회_시_정해진_size_이내로_반환한다() {
            //given
            Brand 인스팅트 = brandRepository.save(인스팅트_식품_브랜드_생성());
            for (int i = 0; i < 20; i++) {
                PetFood 미국_영양기준_만족_식품 = savePetFood(인스팅트);
                primaryIngredientRepository.save(원재료_돼지고기_식품(미국_영양기준_만족_식품));
                functionalityRepository.save(기능성_짱짱(미국_영양기준_만족_식품));
            }

            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoodsByFilters(
                    FilterDto.of(
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST,
                            EMPTY_LIST
                    ),
                    null,
                    size
            );

            // then
            assertThat(petFoods.size()).isEqualTo(20);
        }

        private PetFood savePetFood(Brand 브랜드) {
            return petFoodRepository.save(미국_영양기준_만족_식품(브랜드));
        }

    }

    @Nested
    class 상세_조회 {

        @Test
        void 식품_상세조회할수_있다() {
            //given
            Brand 브랜드 = brandRepository.findAll().get(0);
            PetFood 테스트용_식품 = 모든_영양기준_만족_식품(브랜드);
            Long 아이디 = petFoodRepository.save(테스트용_식품).getId();

            //when
            GetPetFoodResponse 응답 = petFoodQueryService.getPetFoodResponse(아이디);

            //then
            assertThat(응답.id()).isEqualTo(테스트용_식품.getId());
            assertThat(응답.name()).isEqualTo(테스트용_식품.getName());
            assertThat(응답.imageUrl()).isEqualTo(테스트용_식품.getImageUrl());
            assertThat(응답.purchaseUrl()).isEqualTo(테스트용_식품.getPurchaseLink());
            assertThat(응답.brand().name()).isEqualTo(브랜드.getName());
            assertThat(응답.brand().foundedYear()).isEqualTo(브랜드.getFoundedYear());
            assertThat(응답.brand().nation()).isEqualTo(브랜드.getNation());
            assertThat(응답.rating()).isEqualTo(0);
            assertThat(응답.reviewCount()).isEqualTo(0);
            assertThat(응답.hasStandard().hasEuStandard()).isTrue();
            assertThat(응답.hasStandard().hasUsStandard()).isTrue();
        }

    }

    @Test
    void 필터에_맞는_식품_count_를_반환할_수_있다() {
        // given, when
        Long count = petFoodQueryService.getPetFoodsCountByFilters(
                FilterDto.of(
                        EMPTY_LIST,
                        EMPTY_LIST,
                        EMPTY_LIST,
                        EMPTY_LIST
                )
        );

        // then
        assertThat(count).isEqualTo(petFoodRepository.findAll().size());
    }

    @Test
    void 필터링에_필요한_식품_데이터를_조회한다() {
        // when
        FilterResponse metadata = petFoodQueryService.getMetadataForFilter();

        // then
        Assertions.assertAll(
                () -> assertThat(metadata.brands()).extracting(BrandResponse::brandName)
                        .contains("아카나", "오리젠", "퓨리나"),
                () -> assertThat(metadata.functionalities()).extracting(FunctionalityResponse::functionality)
                        .contains("튼튼", "짱짱", "다이어트"),
                () -> assertThat(metadata.mainIngredients()).extracting(PrimaryIngredientResponse::ingredients)
                        .contains("소고기", "돼지고기", "닭고기"),
                () -> assertThat(metadata.nutritionStandards()).extracting(NutrientStandardResponse::nation)
                        .contains("미국", "유럽")
        );
    }

}
