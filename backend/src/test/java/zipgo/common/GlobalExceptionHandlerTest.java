package zipgo.common;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import zipgo.common.acceptance.AcceptanceTest;
import zipgo.common.error.ErrorResponse;
import zipgo.review.presentation.ReviewController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GlobalExceptionHandlerTest extends AcceptanceTest {

    @MockBean(name = "reviewController")
    private ReviewController reviewController;

    @Test
    void 서버_내부_에러가_발생하면_500을_반환한다() {
        // given
        given(reviewController.getReview(any(), anyLong()))
                .willThrow(new TestException());

        // when
        ExtractableResponse<Response> response = RestAssured
                .when().get("/reviews/1")
                .then().extract();

        // then
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(errorResponse.message()).isEqualTo("서버에서 알 수 없는 오류가 발생했습니다.");
    }

    @Test
    void 스프링_예외가_발생했을때_에러_응답_형식을_반환한다() {
        // given
        given(reviewController.getReview(any(), anyLong()))
                .willAnswer((a) -> {
                    throw new HttpMediaTypeNotSupportedException("스프링 예외 메시지");
                });

        // when
        ExtractableResponse<Response> response = RestAssured
                .when().get("/reviews/1")
                .then().extract();

        // then
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.message()).isEqualTo("스프링 예외 메시지");
    }

    class TestException extends RuntimeException {

        TestException() {
            super("테스트 예외입니다.");
        }

    }

}
