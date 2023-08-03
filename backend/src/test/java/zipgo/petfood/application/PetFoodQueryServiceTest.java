package zipgo.petfood.application;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.KeywordRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.presentation.dto.FilterResponse;
import zipgo.petfood.presentation.dto.GetPetFoodResponse;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_없이_식품_초기화;
import static zipgo.petfood.domain.fixture.PetFoodFixture.키워드_있는_식품_초기화;

@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@Sql(scripts = {"classpath:truncate.sql", "classpath:data.sql"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PetFoodQueryServiceTest {

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PetFoodQueryService petFoodQueryService;

    private Brand 브랜드_조회하기() {
        return brandRepository.findAll().get(0);
    }

    @Nested
    class 필터_조회 {

        @Test
        void 브랜드를_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoods(
                    List.of("퓨리나"),
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).extracting(PetFood::getName)
                            .contains("[고집] 갈비 맛 모밀"),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getId())
                            .contains(2L)
            );
        }

        @Test
        void 영양_기준을_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoods(
                    EMPTY_LIST,
                    List.of("유럽"),
                    EMPTY_LIST,
                    EMPTY_LIST
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).extracting(PetFood::getName)
                            .contains("[고집] 돌아온 배배", "[고집] 말미잘 맛 민무"),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getHasStandard().getEurope())
                            .contains(true)
            );
        }

        @Test
        void 주원료를_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoods(
                    EMPTY_LIST,
                    EMPTY_LIST,
                    List.of("말미잘"),
                    EMPTY_LIST
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).extracting(PetFood::getName)
                            .contains("[고집] 돌아온 배배", "[고집] 말미잘 맛 민무"),
                    () -> assertThat(petFoods).extracting(PetFood::getPrimaryIngredients)
                            .contains(List.of("말미잘"))
            );
        }

        @Test
        void 기능성을_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoods(
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST,
                    List.of("튼튼")
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).extracting(PetFood::getName)
                            .contains("[고집] 돌아온 배배"),
                    () -> assertThat(petFoods).extracting(PetFood::getFunctionality)
                            .contains(List.of("튼튼"))
            );
        }

        @Test
        void 모든_필터를_만족하는_식품만_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoods(
                    List.of("오리젠"),
                    List.of("유럽"),
                    List.of("닭고기"),
                    List.of("튼튼")
            );

            // then
            assertAll(
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getBrand().getId())
                            .contains(1L),
                    () -> assertThat(petFoods).extracting(petFood -> petFood.getHasStandard().getEurope())
                            .contains(true),
                    () -> assertThat(petFoods).extracting(PetFood::getFunctionality)
                            .contains(List.of("튼튼")),
                    () -> assertThat(petFoods).extracting(PetFood::getPrimaryIngredients)
                            .contains(List.of("닭고기", "말미잘"))
            );
        }

        @Test
        void 모든_정보가_NULL일_경우_모든_식품을_반환한다() {
            // when
            List<PetFood> petFoods = petFoodQueryService.getPetFoods(
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST,
                    EMPTY_LIST
            );

            // then
            assertThat(petFoods.size()).isEqualTo(petFoodRepository.findAll().size());
        }

    }

    @Nested
    class 상세_조회 {

        @Test
        void 식품_상세조회할수_있다() {
            //given
            Brand 브랜드 = brandRepository.findAll().get(0);
            PetFood 테스트용_식품 = 키워드_없이_식품_초기화(브랜드);
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
            assertThat(응답.functionality()).containsAll(테스트용_식품.getFunctionality());
        }

    }

    @Test
    void 키워드가_다이어트인_식품_목록을_조회한다() {
        // given
        PetFood 키워드가_없는_식품 = petFoodRepository.save(키워드_없이_식품_초기화(브랜드_조회하기()));
        PetFood 다이어트_키워드_식품 = 다이어트_키워드_식품_저장();

        // when
        List<PetFood> 조회_결과 = petFoodQueryService.getPetFoodByDynamicValue("diet", null, null);

        // then
        assertAll(() -> {
            assertThat(조회_결과).contains(다이어트_키워드_식품);
            assertThat(조회_결과).doesNotContain(키워드가_없는_식품);
        });
    }

    private PetFood 다이어트_키워드_식품_저장() {
        Keyword 다이어트_키워드 = keywordRepository.findByName("diet").orElseThrow();
        PetFood 다이어트_키워드_식품 = petFoodRepository.save(키워드_있는_식품_초기화(다이어트_키워드, 브랜드_조회하기()));
        return 다이어트_키워드_식품;
    }

    @Test
    void 존재하지_않는_키워드로_조회한다() {
        // given
        String 없는_키워드 = "없는 키워드";

        // when
        List<PetFood> responses = petFoodQueryService.getPetFoodByDynamicValue(없는_키워드, null, null);

        // then
        assertThat(responses).hasSize(0);
    }


    @Test
//    @Disabled("230802 메타데이터 API 요구사항 변경사항 반영 전 비활성화")
    void 필터링에_필요한_식품_데이터를_조회한다() {
        // given
        Keyword keyword = keywordRepository.save(new Keyword(1L, "diet"));
        Brand brand = brandRepository.save(BrandFixture.식품_브랜드_생성하기());
        petFoodRepository.save(키워드_있는_식품_초기화(keyword, brand));

        // when
        FilterResponse metadata = petFoodQueryService.getMetadataForFilter();

        // then
//        assertAll(
//                () -> assertThat(metadata.brands())
//                        .extracting(BrandResponse::brandName).contains("오리젠", "퓨리나"),
//                () -> assertThat(metadata.functionalities())
//                        .contains("튼튼", "짱짱"),
//                () -> assertThat(metadata.mainIngredients())
//                        .contains("닭고기", "쌀", "말미잘"),
//                () -> assertThat(metadata.nutritionStandards())
//                        .contains("유럽", "미국")
//        );
    }

}
