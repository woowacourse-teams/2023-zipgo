package zipgo.acceptance;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.epages.restdocs.apispec.Schema;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class PetFoodAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("식품 목록 조회 API")
    class GetPetFoods {

        @Test
        void 키워드를_지정하지_않고_요청한다() {
            // given
            var 요청_준비 = given(spec)
                    .contentType(ContentType.JSON)
                    .filter(식품_목록_조회_API_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(HttpStatus.OK.value())
                    .assertThat().body("foodList.size()", is(2));
        }

        private RestDocumentationFilter 식품_목록_조회_API_문서_생성() {
            Schema 응답_형식 = Schema.schema("GetPetFoodResponse");
            ResourceSnippetDetails 문서_정보 = resourceDetails().summary("식품 목록 조회 성공")
                    .description("모든 반려동물 식품을 조회합니다.")
                    .responseSchema(응답_형식);

            return RestAssuredRestDocumentationWrapper.document("식품 목록 조회 성공",
                    문서_정보,
                    queryParameters(parameterWithName("keyword").optional().description("식품 조회 API")),
                    responseFields(
                            fieldWithPath("foodList").description("반려동물 식품 리스트"),
                            fieldWithPath("foodList[].id").description("식품 id"),
                            fieldWithPath("foodList[].name").description("식품 이름"),
                            fieldWithPath("foodList[].imageUrl").description("식품 이미지 url"),
                            fieldWithPath("foodList[].purchaseUrl").description("구매 링크")
                    ));
        }

        @Test
        void 키워드가_있는_목록을_요청한다() {

            // given
            String 키워드 = "diet";
            var 요청_준비 = given()
                    .contentType(ContentType.JSON)
                    .queryParam("keyword", 키워드);

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(HttpStatus.OK.value())
                    .assertThat().body("foodList.size()", not(empty()));
        }

        @Test
        void 존재하지_않는_키워드로_요청한다() {
            // given
            var 요청_준비 = given(spec)
                    .contentType(ContentType.JSON)
                    .filter(존재하지_않는_키워드_예외_문서_생성())
                    .queryParam("keyword", "존재하지 않는 키워드");

            // when
            var 응답 = 요청_준비.when()
                    .get("/pet-foods");

            // then
            응답.then()
                    .assertThat().statusCode(HttpStatus.NOT_FOUND.value())
                    .assertThat().body("message", containsString("키워드를 찾을 수 없습니다."));

        }

        private RestDocumentationFilter 존재하지_않는_키워드_예외_문서_생성() {
            Schema 응답_형식 = Schema.schema("ErrorResponse");
            ResourceSnippetDetails 문서_정보 = resourceDetails().summary("식품 목록 조회 성공")
                    .description("모든 반려동물 식품을 조회합니다.")
                    .responseSchema(응답_형식);

            return RestAssuredRestDocumentationWrapper.document("식품 목록 조회 실패", 문서_정보);
        }

    }

}
