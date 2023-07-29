package zipgo.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import zipgo.acceptance.AcceptanceTest;
import zipgo.petfood.presentation.PetFoodController;
import zipgo.petfood.presentation.dto.ErrorResponse;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GlobalExceptionHandlerTest extends AcceptanceTest {

    @SpyBean(name = "petFoodController")
    private PetFoodController petFoodController;

    @Test
    void 서버_내부_에러가_발생하면_500을_반환한다() {
        //given
        given(petFoodController.getPetFoods(any())).willThrow(TestException.class);

        //when
        ExtractableResponse<Response> response = RestAssured
                .when().get("/pet-foods")
                .then().extract();

        //then
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.message()).isEqualTo("서버 내부 오류");
    }

    @Test
    void 스프링_예외가_발생했을때_에러_응답_형식을_반환한다() {
        //given
        given(petFoodController.getPetFoods(any())).willAnswer((a) -> {
            throw new HttpMediaTypeNotSupportedException("스프링 예외 메시지");
        });

        //when
        ExtractableResponse<Response> response = RestAssured
                .when().get("/pet-foods")
                .then().extract();

        //then
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.message()).isEqualTo("스프링 예외 메시지");
    }

    class TestException extends RuntimeException {

        TestException() {
            super("테스트 예외입니다.");
        }

    }

}
