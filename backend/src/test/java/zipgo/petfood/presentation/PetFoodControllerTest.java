package zipgo.petfood.presentation;

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.acceptance.AcceptanceTest;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;

import java.util.List;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static zipgo.brand.domain.fixture.BrandFixture.아카나_식품_브랜드_생성;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_다이어트;
import static zipgo.petfood.domain.fixture.FunctionalityFixture.기능성_튼튼;
import static zipgo.petfood.domain.fixture.PetFoodFixture.모든_영양기준_만족_식품;
import static zipgo.petfood.domain.fixture.PetFoodFixture.식품_저장;
import static zipgo.petfood.domain.fixture.PetFoodFunctionalityFixture.식품_기능성_추가;
import static zipgo.petfood.domain.fixture.PetFoodPrimaryIngredientFixture.식품_주원료_추가;
import static zipgo.petfood.domain.fixture.PrimaryIngredientFixture.주원료_닭고기;

public class PetFoodControllerTest extends AcceptanceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private PetFoodRepository petFoodRepository;

    @Autowired
    private PrimaryIngredientRepository primaryIngredientRepository;

    @Autowired
    private FunctionalityRepository functionalityRepository;

    @Nested
    @DisplayName("식품 전체 조회 API")
    class GetPetFoods {

        private Schema 성공_응답_형식 = schema("GetPetFoodsResponse");

        private ResourceSnippetDetails API_정보 = resourceDetails()
                .summary("식품 전체 조회하기")
                .description("식품 전체를 조회합니다.");

        @Test
        void 필터를_지정하지_않고_요청한다() {
            // given
            PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(brandRepository.save(아카나_식품_브랜드_생성()));

            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_다이어트(), functionalityRepository);
            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_튼튼(), functionalityRepository);

            식품_주원료_추가(모든_영양기준_만족_식품, 주원료_닭고기(), primaryIngredientRepository);

            식품_저장(petFoodRepository, 모든_영양기준_만족_식품);

            var 요청_준비 = given(spec)
                    .queryParam("size", 20)
                    .filter(성공_API_문서_생성("식품 필터링 없이 조회 - 성공(전체 조회)"));

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        @Test
        void 필터를_지정해서_요청한다() {
            // given
            PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(brandRepository.save(아카나_식품_브랜드_생성()));

            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_다이어트(), functionalityRepository);
            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_튼튼(), functionalityRepository);

            식품_주원료_추가(모든_영양기준_만족_식품, 주원료_닭고기(), primaryIngredientRepository);

            식품_저장(petFoodRepository, 모든_영양기준_만족_식품);

            List<String> 브랜드 = List.of("아카나");
            List<String> 영양기준 = List.of("유럽");
            List<String> 기능성 = List.of("튼튼");
            List<String> 주단백질원 = List.of("닭고기");

            var 요청_준비 = given(spec)
                    .queryParam("brands", 브랜드)
                    .queryParam("nutritionStandards", 영양기준)
                    .queryParam("functionalities", 기능성)
                    .queryParam("mainIngredients", 주단백질원)
                    .queryParam("size", 20)
                    .filter(성공_API_문서_생성("식품 필터링 조회 - 성공"));

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        private RestDocumentationFilter 성공_API_문서_생성(String name) {
            return document(name,
                    API_정보.responseSchema(성공_응답_형식),
                    queryParameters(
                            parameterWithName("brands").description("브랜드(optional)").optional(),
                            parameterWithName("nutritionStandards").description("영양기준(optional)").optional(),
                            parameterWithName("functionalities").description("기능성(optional)").optional(),
                            parameterWithName("mainIngredients").description("주영양소(optional)").optional(),
                            parameterWithName("lastPetFoodId").description("마지막식품Id(제일 처음에 null)").optional(),
                            parameterWithName("size").description("식품 페이징 사이즈").optional()
                    ),
                    responseFields(
                            fieldWithPath("totalCount").description("전체 식품 개수").type(JsonFieldType.NUMBER),
                            fieldWithPath("petFoods").description("반려동물 식품 리스트").type(JsonFieldType.ARRAY),
                            fieldWithPath("petFoods[].id").description("식품 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("petFoods[].imageUrl").description("식품 이미지 url").type(JsonFieldType.STRING),
                            fieldWithPath("petFoods[].brandName").description("브랜드 이름").type(JsonFieldType.STRING),
                            fieldWithPath("petFoods[].foodName").description("식품 이름").type(JsonFieldType.STRING)
                    ));
        }

    }

    @Nested
    @DisplayName("식품 상세 조회 API")
    class GetPetFood {

        private Schema 성공_응답_형식 = schema("GetPetFoodResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails()
                .summary("식품 상세 정보 조회하기")
                .description("id에 해당하는 식품 상세정보를 조회합니다.");

        @Test
        void 올바른_요청() {
            // given
            PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(brandRepository.save(아카나_식품_브랜드_생성()));

            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_다이어트(), functionalityRepository);
            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_튼튼(), functionalityRepository);

            식품_주원료_추가(모든_영양기준_만족_식품, 주원료_닭고기(), primaryIngredientRepository);

            식품_저장(petFoodRepository, 모든_영양기준_만족_식품);

            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(식품_상세_조회_API_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .pathParam("id", 모든_영양기준_만족_식품.getId())
                    .get("/pet-foods/{id}");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        private RestDocumentationFilter 식품_상세_조회_API_문서_생성() {
            return document("식품 상세 조회 - 성공",
                    문서_정보.responseSchema(성공_응답_형식),
                    pathParameters(parameterWithName("id").description("조회할 상품 id")),
                    responseFields(
                            fieldWithPath("id").description("식품 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("name").description("식품 이름").type(JsonFieldType.STRING),
                            fieldWithPath("imageUrl").description("식품 이미지 url").type(JsonFieldType.STRING),
                            fieldWithPath("purchaseUrl").description("구매링크").type(JsonFieldType.STRING),
                            fieldWithPath("rating").description("평균 별점").type(JsonFieldType.NUMBER),
                            fieldWithPath("reviewCount").description("리뷰수").type(JsonFieldType.NUMBER),
                            fieldWithPath("primaryIngredients").description("주 원료").type(JsonFieldType.ARRAY),
                            fieldWithPath("hasStandard.us").description("미국 기준 충족 여부").type(JsonFieldType.BOOLEAN),
                            fieldWithPath("hasStandard.eu").description("유럽 기준 충족 여부").type(JsonFieldType.BOOLEAN),
                            fieldWithPath("functionality").description("기능성").type(JsonFieldType.ARRAY),
                            fieldWithPath("brand.name").description("브랜드명").type(JsonFieldType.STRING),
                            fieldWithPath("brand.imageUrl").description("브랜드 이미지 url").type(JsonFieldType.STRING),
                            fieldWithPath("brand.state").description("브랜드 국가").type(JsonFieldType.STRING),
                            fieldWithPath("brand.foundedYear").description("브랜드 설립연도").type(JsonFieldType.NUMBER),
                            fieldWithPath("brand.hasResearchCenter").description("브랜드 연구 기관 존재 여부")
                                    .type(JsonFieldType.BOOLEAN),
                            fieldWithPath("brand.hasResidentVet").description("상주 수의사 존재 여부")
                                    .type(JsonFieldType.BOOLEAN)
                    ));
        }

        @Test
        void 존재하지_않는_아이디로_요청한다() {
            //given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .pathParam("id", 1000000000000232323L)
                    .get("/pet-foods/{id}");

            // then
            응답.then()
                    .assertThat().statusCode(NOT_FOUND.value());
        }

        @Test
        void 올바르지_않은_형식의_아이디로_요청한다() {
            //given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .pathParam("id", "아이디")
                    .get("/pet-foods/{id}");

            // then
            응답.then()
                    .assertThat().statusCode(BAD_REQUEST.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성() {
            return document("식품 상세 조회 - 실패(올바르지 않은 형식)", 문서_정보.responseSchema(에러_응답_형식));
        }

    }

    @Nested
    @DisplayName("필터링 조회")
    class GetFilterInfo {

        private RestDocumentationFilter 필터링_메타데이터_API_문서_생성() {
            return document("필터링 메타데이터 정보 조회 - 성공",
                    문서_정보.responseSchema(성공_응답_형식),
                    responseFields(
                            fieldWithPath("keywords").type(ARRAY).description("필터 키워드 목록"),
                            fieldWithPath("filters").type(OBJECT).description("필터 정보"),
                            fieldWithPath("filters.brands[]").type(ARRAY).description("브랜드"),
                            fieldWithPath("filters.brands[].id").type(NUMBER).description("브랜드 아이디"),
                            fieldWithPath("filters.brands[].brandName").type(STRING).description("브랜드 이름"),
                            fieldWithPath("filters.brands[].brandUrl").type(STRING).description("브랜드 이미지 url"),
                            fieldWithPath("filters.nutritionStandards[]").type(ARRAY).description("영양기준"),
                            fieldWithPath("filters.nutritionStandards[].id").type(NUMBER).description("영양기준 아이디"),
                            fieldWithPath("filters.nutritionStandards[].nation").type(STRING).description("영양기준 국가"),
                            fieldWithPath("filters.mainIngredients[]").type(ARRAY).description("주원료 배열"),
                            fieldWithPath("filters.mainIngredients[].id").type(NUMBER).description("주원료 아이디"),
                            fieldWithPath("filters.mainIngredients[].ingredients").type(STRING).description("주원료 이름"),
                            fieldWithPath("filters.functionalities[]").type(ARRAY).description("기능성 배열"),
                            fieldWithPath("filters.functionalities[].id").type(NUMBER).description("기능성 아이디"),
                            fieldWithPath("filters.functionalities[].functionality").type(STRING).description("기능성 이름")
                    ));
        }

        private Schema 성공_응답_형식 = schema("FilterMetadataResponse");

        private ResourceSnippetDetails 문서_정보 = resourceDetails()
                .summary("필터링 메타데이터 정보 조회하기")
                .description("필터링에 필요한 메타데이터 정보를 조회합니다.");

        @Test
        void 필터링에_필요한_메타데이터를_조회한다() {
            // given
            PetFood 모든_영양기준_만족_식품 = 모든_영양기준_만족_식품(brandRepository.save(아카나_식품_브랜드_생성()));

            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_다이어트(), functionalityRepository);
            식품_기능성_추가(모든_영양기준_만족_식품, 기능성_튼튼(), functionalityRepository);

            식품_주원료_추가(모든_영양기준_만족_식품, 주원료_닭고기(), primaryIngredientRepository);

            식품_저장(petFoodRepository, 모든_영양기준_만족_식품);

            // when
            var 응답 = given(spec)
                    .filter(필터링_메타데이터_API_문서_생성())
                    .when()
                    .get("/pet-foods/filters");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

    }

}
