package zipgo.acceptance;

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.test.context.jdbc.Sql;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

public class PetFoodAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("식품 전체 조회 API")
    class GetPetFoods {

        private Schema 성공_응답_형식 = schema("GetPetFoodsResponse");
        private ResourceSnippetDetails API_정보 = resourceDetails()
                .summary("식품 전체 조회하기")
                .description("식품 전체를 조회합니다.");

        @Test
        void 키워드를_지정하지_않고_요청한다() {
            // given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(성공_API_문서_생성("식품 전체 조회 - 성공(키워드 없음)"));

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("petFoods.size()", is(2));
        }

        private RestDocumentationFilter 성공_API_문서_생성(String name) {
            return document(name,
                    API_정보.responseSchema(성공_응답_형식),
                    queryParameters(parameterWithName("keyword").optional().description("식품 키워드")),
                    responseFields(
                            fieldWithPath("petFoods").description("반려동물 식품 리스트").type(JsonFieldType.ARRAY),
                            fieldWithPath("petFoods[].id").description("식품 id").type(JsonFieldType.NUMBER),
                            fieldWithPath("petFoods[].name").description("식품 이름").type(JsonFieldType.STRING),
                            fieldWithPath("petFoods[].imageUrl").description("식품 이미지 url").type(JsonFieldType.STRING),
                            fieldWithPath("petFoods[].purchaseUrl").description("구매 링크").type(JsonFieldType.STRING)
                    ));
        }

        @Test
        void 키워드가_있는_목록을_요청한다() {

            // given
            String 키워드 = "diet";
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .queryParam("keyword", 키워드)
                    .filter(성공_API_문서_생성("식품 전체 조회 - 성공(키워드 지정)"));

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("petFoods.size()", not(empty()));
        }

        @Test
        void 존재하지_않는_키워드로_요청한다() {
            // given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(존재하지_않는_키워드_예외_문서_생성())
                    .queryParam("keyword", "존재하지 않는 키워드");

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(NOT_FOUND.value())
                    .assertThat().body("message", containsString("키워드를 찾을 수 없습니다."));

        }

        private RestDocumentationFilter 존재하지_않는_키워드_예외_문서_생성() {
            return document("식품 전체 조회 - 실패(존재하지 않는 키워드)", API_정보.responseSchema(에러_응답_형식));
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
            Long 모의_식품_아이디 = 모의_식품_생성();

            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(식품_상세_조회_API_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .pathParam("id", 모의_식품_아이디)
                    .get("/pet-foods/{id}");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value());
        }

        @Sql("./insert-pet-food.sql")
        private Long 모의_식품_생성() {
            // todo: 데이터베이스 추가
            return 1L;
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
                            fieldWithPath("brand.imageUrl").description("브랜드 사진 url").type(JsonFieldType.STRING),
                            fieldWithPath("brand.state").description("브랜드 국가").type(JsonFieldType.STRING),
                            fieldWithPath("brand.foundedYear").description("브랜드 설립연도").type(JsonFieldType.NUMBER),
                            fieldWithPath("brand.hasResearchCenter").description("브랜드 연구 기관 존재 여부")
                                    .type(JsonFieldType.BOOLEAN),
                            fieldWithPath("brand.hasResidentVet").description("상주 수의사 존재 여부")
                                    .type(JsonFieldType.BOOLEAN)
                    ));
        }

        @Test
        void 존재하지_않는_아이디로_요청한다() { // TODO: 예외처리
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

}
