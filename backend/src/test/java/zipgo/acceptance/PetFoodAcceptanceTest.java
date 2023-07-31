package zipgo.acceptance;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
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

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.test.context.jdbc.Sql;
import zipgo.petfood.presentation.dto.PetFoodSelectRequest;

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
            String 키워드 = "";
            String 브랜드 = "오리젠";
            String 주단백질원 = "말미잘";

            PetFoodSelectRequest request = new PetFoodSelectRequest(키워드, 브랜드, 주단백질원);

            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .body(request)
                    .filter(성공_API_문서_생성("식품 전체 조회 - 성공(키워드 없음)"));

            // when
            var 응답 = 요청_준비.when()
                    .post("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("petFoods.size()", is(1));
        }

        private RestDocumentationFilter 성공_API_문서_생성(String name) {
            return document(name,
                    API_정보.responseSchema(성공_응답_형식),
                    responseFields(
                            fieldWithPath("petFoods").description("반려동물 식품 리스트"),
                            fieldWithPath("petFoods[].id").description("식품 id"),
                            fieldWithPath("petFoods[].name").description("식품 이름"),
                            fieldWithPath("petFoods[].imageUrl").description("식품 이미지 url"),
                            fieldWithPath("petFoods[].purchaseUrl").description("구매 링크")
                    ));
        }

        @Test
        void 키워드가_있는_목록을_요청한다() {
            // given
            String 키워드 = "diet";
            String 브랜드 = "오리젠";
            String 주단백질원 = "말미잘";

            PetFoodSelectRequest request = new PetFoodSelectRequest(키워드, 브랜드, 주단백질원);

            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .body(request)
                    .filter(성공_API_문서_생성("식품 전체 조회 - 성공(키워드 지정)"));

            // when
            var 응답 = 요청_준비.when()
                    .post("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("petFoods.size()", not(empty()));
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
                            fieldWithPath("id").description("식품 id"),
                            fieldWithPath("name").description("식품 이름"),
                            fieldWithPath("imageUrl").description("식품 이미지 url"),
                            fieldWithPath("purchaseUrl").description("구매링크"),
                            fieldWithPath("rating").description("평균 별점"),
                            fieldWithPath("reviewCount").description("리뷰수"),
                            fieldWithPath("primaryIngredients").description("주 원료"),
                            fieldWithPath("hasStandard.us").description("미국 기준 충족 여부"),
                            fieldWithPath("hasStandard.eu").description("유럽 기준 충족 여부"),
                            fieldWithPath("functionality").description("기능성"),
                            fieldWithPath("brand.name").description("브랜드명"),
                            fieldWithPath("brand.state").description("브랜드 국가"),
                            fieldWithPath("brand.foundedYear").description("브랜드 설립연도"),
                            fieldWithPath("brand.hasResearchCenter").description("브랜드 연구 기관 존재 여부"),
                            fieldWithPath("brand.hasResidentVet").description("상주 수의사 존재 여부")
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
