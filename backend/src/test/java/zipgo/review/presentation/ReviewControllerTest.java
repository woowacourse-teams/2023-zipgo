package zipgo.review.presentation;

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.acceptance.AcceptanceTest;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static zipgo.review.fixture.ReviewFixture.리뷰_생성_요청;
import static zipgo.review.fixture.ReviewFixture.리뷰_수정_요청;

public class ReviewControllerTest extends AcceptanceTest {

    //TODO 갈비꺼 merge 후 고정 상수 리팩터링하기

    @Nested
    @DisplayName("리뷰 전체 목록 조회 API")
    class GetReviews {

        private Schema 성공_응답_형식 = schema("GetReviewsResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails()
                .summary("리뷰 전체 목록 조회하기")
                .description("해당 반려동물 식품에 대한 모든 리뷰를 조회합니다.");

        @Test
        void 리뷰를_조회하면_201_반환() {
            // given
            var 요청_준비 = given(spec)
                    .contentType(JSON)
                    .filter(리뷰_목록_조회_API_문서_생성());

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

        private RestDocumentationFilter 리뷰_목록_조회_API_문서_생성() {
            return document("리뷰 전체 조회 - 성공",
                    문서_정보.responseSchema(성공_응답_형식),
                    pathParameters(parameterWithName("id").description("식품 id")),
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

    @Nested
    @DisplayName("리뷰 생성 API")
    class CreateReviews {

        private Schema 성공_응답_형식 = schema("CreateReviewResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails()
                .summary("리뷰 생성하기")
                .description("해당 반려동물 식품에 대한 리뷰를 생성합니다.");

        @Test
        void 리뷰를_성공적으로_생성하면_201_반환() {
            // given
            //TODO 여기도 갈비씨가 member pr 날려주면 리팩토링 할 듯
            var 요청_준비 = given(spec)
                    .queryParam("memberId", 1L)
                    .body(리뷰_생성_요청(1L))
                    .contentType(JSON)
                    .filter(리뷰_생성_API_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .post("/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(CREATED.value());
        }

        private RestDocumentationFilter 리뷰_생성_API_문서_생성() {
            return document("리뷰 생성 - 성공",
                    문서_정보.responseSchema(성공_응답_형식)
            );
        }

        @Test
        void 없는_식품에_대해_리뷰를_생성하면_404_반환() {
            // given
            var 요청_준비 = given(spec)
                    .queryParam("memberId", 1L)
                    .body(리뷰_생성_요청(123456789L))
                    .contentType(JSON)
                    .filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .post("/reviews");

            // then
            응답.then()
                    .assertThat().statusCode(NOT_FOUND.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성() {
            return document("리뷰 생성 - 실패(없는 식품)", 문서_정보.responseSchema(에러_응답_형식));
        }

    }

    @Nested
    @DisplayName("리뷰 수정 API")
    class UpdateReviews {

        private Schema 성공_응답_형식 = schema("UpdateReviewResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails()
                .summary("리뷰 수정하기")
                .description("해당 반려동물 식품에 대한 리뷰를 수정합니다.");

        @Test
        void 리뷰를_성공적으로_수정하면_204_반환() {
            // given
            var 요청_준비 = given(spec)
                    .queryParam("memberId", 1L)
                    .body(리뷰_수정_요청())
                    .contentType(JSON)
                    .filter(리뷰_수정_API_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .put("/reviews/" + 1);

            // then
            응답.then()
                    .assertThat().statusCode(NO_CONTENT.value());
        }

        private RestDocumentationFilter 리뷰_수정_API_문서_생성() {
            return document("리뷰 수정 - 성공",
                    문서_정보.responseSchema(성공_응답_형식)
            );
        }

        @Test
        void 리뷰를_쓴_사람이_아닌_멤버가_리뷰를_수정하면_403_반환() {
            // given
            var 요청_준비 = given(spec)
                    .queryParam("memberId", 2L)
                    .body(리뷰_생성_요청(123456789L))
                    .contentType(JSON)
                    .filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .put("/reviews/" + 1);

            // then
            응답.then()
                    .assertThat().statusCode(FORBIDDEN.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성() {
            return document("리뷰 수정 - 실패(Forbidden)", 문서_정보.responseSchema(에러_응답_형식));
        }

    }

    @Nested
    @DisplayName("리뷰 삭제 API")
    class DeleteReviews {

        private Schema 성공_응답_형식 = schema("DeleteReviewResponse");
        private ResourceSnippetDetails 문서_정보 = resourceDetails()
                .summary("리뷰 삭제하기")
                .description("해당 반려동물 식품에 대한 리뷰를 삭제합니다.");

        @Test
        void 리뷰를_성공적으로_삭제하면_204_반환() {
            // given
            var 요청_준비 = given(spec)
                    .queryParam("memberId", 1L)
                    .body(리뷰_수정_요청())
                    .contentType(JSON)
                    .filter(리뷰_삭제_API_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .delete("/reviews/" + 1);

            // then
            응답.then()
                    .assertThat().statusCode(NO_CONTENT.value());
        }

        private RestDocumentationFilter 리뷰_삭제_API_문서_생성() {
            return document("리뷰 삭제 - 성공",
                    문서_정보.responseSchema(성공_응답_형식)
            );
        }

        @Test
        void 리뷰를_쓴_사람이_아닌_멤버가_리뷰를_삭제하면_403_반환() {
            // given
            var 요청_준비 = given(spec)
                    .queryParam("memberId", 2L)
                    .body(리뷰_생성_요청(123456789L))
                    .contentType(JSON)
                    .filter(API_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when()
                    .delete("/reviews/" + 1);

            // then
            응답.then()
                    .assertThat().statusCode(FORBIDDEN.value());
        }

        private RestDocumentationFilter API_예외응답_문서_생성() {
            return document("리뷰 삭제 - 실패(Forbidden)", 문서_정보.responseSchema(에러_응답_형식));
        }

    }

}
