package zipgo.acceptance;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.epages.restdocs.apispec.Schema;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

public class ReviewAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("리뷰 전체 목록 조회 API")
    class GetReviews {

        @Test
        void 키워드를_지정하지_않고_요청한다() {
            // given
            var 요청_준비 = given(spec)
                    .contentType(JSON);

            // when
            //TODO petFoodId가 data.sql에 종속적인거 같아서 리뷰 생성 기능 만든 후 리팩터링 예정
            var 응답 = 요청_준비.when()
                    .get("/pet-foods/" + 1 + "/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("reviews.size()", is(2));
        }

        private RestDocumentationFilter 식품_목록_조회_API_문서_생성() {
            var 응답_형식 = schema("GetReviewsResponse");
            var 문서_정보 = resourceDetails().summary("리뷰 전체 목록 조회 성공")
                    .description("해당 반려동물 식품에 대한 모든 리뷰를 조회합니다.")
                    .responseSchema(응답_형식);

            return document("리뷰 전체 목록 조회 성공",
                    문서_정보,
                    queryParameters(parameterWithName("keyword").optional().description("리뷰 전체 조회 API")),
                    responseFields(
                            fieldWithPath("id").description("리뷰 id"),
                            fieldWithPath("reviewrName").description("리뷰 작성자 이름"),
                            fieldWithPath("rating").description("리뷰 별점"),
                            fieldWithPath("date").description("리뷰 생성일"),
                            fieldWithPath("comment").description("리뷰 코멘트"),
                            fieldWithPath("tastePreference").description("기호성"),
                            fieldWithPath("stoolCondition").description("대변 상태"),
                            fieldWithPath("adverseReactions").description("이상 반응들")
                    ));
        }
    }

}
