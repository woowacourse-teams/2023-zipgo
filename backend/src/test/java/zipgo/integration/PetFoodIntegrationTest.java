package zipgo.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import zipgo.controller.dto.GetPetFoodsResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class PetFoodIntegrationTest extends IntegrationTest {
    @Test
    void 모든_식품_조회_API() {
        ExtractableResponse<Response> response = given().contentType(ContentType.JSON)
                .log().all()
                .when().get("/pet-foods")
                .then().extract();

        GetPetFoodsResponse data = response.body()
                .jsonPath().getObject("data", GetPetFoodsResponse.class);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(data.petFoods()).isNotEmpty();
    }
}
