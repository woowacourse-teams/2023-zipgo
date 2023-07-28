package zipgo.review.presentation;

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
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.acceptance.AcceptanceTest;

public class ReviewControllerTest extends AcceptanceTest {

    @Nested
    @DisplayName("리뷰 전체 목록 조회 API")
    class GetReviews {

        private Schema 성공_응답_형식 = schema("GetReviewsResponse");
        private ResourceSnippetDetails API_정보 = resourceDetails()
                .summary("리뷰 전체 목록 조회하기")
                .description("해당 반려동물 식품에 대한 모든 리뷰를 조회합니다.");

        @Test
        void 키워드를_지정하지_않고_요청한다() {
            // given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(식품_목록_조회_API_문서_생성());

            // when
            //TODO petFoodId가 data.sql에 종속적인거 같아서 리뷰 생성 기능 만든 후 리팩터링 예정
            var 응답 = 요청_준비.when()
                    .pathParam("id", 1)
                    .get("/pet-foods/{id}/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(OK.value())
                    .assertThat().body("reviews.size()", is(2));
        }

        private RestDocumentationFilter 식품_목록_조회_API_문서_생성() {
            return document("성공",
                    API_정보.responseSchema(성공_응답_형식),
                    pathParameters(parameterWithName("id").description("식품 id")),
                    queryParameters(parameterWithName("keyword").optional().description("식품 키워드")),
                    responseFields(
                            fieldWithPath("[].id").description("리뷰 id"),
                            fieldWithPath("[].reviewerName").description("리뷰 작성자 이름"),
                            fieldWithPath("[].rating").description("리뷰 별점"),
                            fieldWithPath("[].date").description("리뷰 생성일"),
                            fieldWithPath("[].comment").description("리뷰 코멘트"),
                            fieldWithPath("[].tastePreference").description("기호성"),
                            fieldWithPath("[].stoolCondition").description("대변 상태"),
                            fieldWithPath("[].adverseReactions").description("이상 반응들")
                    ));
        }

    }

}
