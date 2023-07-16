package zipgo.acceptance;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import zipgo.controller.dto.GetPetFoodsResponse;

public class PetFoodAcceptanceTest extends AcceptanceTest {
    @Test
    void 모든_식품_조회_API() {
        ExtractableResponse<Response> response = given().contentType(ContentType.JSON)
                .when().get("/pet-foods")
                .then().extract();

        GetPetFoodsResponse data = response.body().as(GetPetFoodsResponse.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(data.petFoods()).isNotEmpty();
    }

    @Test
    void 키워드가_다이어트인_식품_조회_API() {
        ExtractableResponse<Response> response = given().contentType(ContentType.JSON)
                .queryParam("keyword", "diet")
                .when().get("/pet-foods")
                .then().extract();

        GetPetFoodsResponse data = response.body().as(GetPetFoodsResponse.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(data.petFoods()).isNotEmpty();
    }

    @Test
    void 존재하지_않는_키워드로_식품_조회_API() {
        ExtractableResponse<Response> response = given().contentType(ContentType.JSON)
                .queryParam("keyword", "존재하지 않는 키워드")
                .when().get("/pet-foods")
                .then().extract();

        assertThat(response.statusCode()).isEqualTo(404);
    }
}