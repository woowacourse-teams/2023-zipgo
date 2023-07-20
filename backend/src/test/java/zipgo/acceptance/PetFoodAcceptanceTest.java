package zipgo.acceptance;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.epages.restdocs.apispec.Schema;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import zipgo.controller.dto.ErrorResponse;
import zipgo.controller.dto.GetPetFoodsResponse;
import zipgo.controller.dto.PetFoodResponse;

public class PetFoodAcceptanceTest extends AcceptanceTest {

    @Test
    void 모든_식품_조회_API() {
        ExtractableResponse<Response> response = given(this.spec).contentType(ContentType.JSON)
                .filter(RestAssuredRestDocumentationWrapper.document("get-pet-foods",
                        resourceDetails().summary("식품 목록 조회").description("모든 반려동물 식품을 조회합니다.")
                                .responseSchema(Schema.schema("GetPetFoodResponse")),
                        queryParameters(parameterWithName("keyword").optional().description("식품 조회 API")),
                        responseFields(
                                fieldWithPath("foodList").description("반려동물 식품 리스트"),
                                fieldWithPath("foodList[].id").description("식품 id"),
                                fieldWithPath("foodList[].name").description("식품 이름"),
                                fieldWithPath("foodList[].imageUrl").description("식품 이미지 url"),
                                fieldWithPath("foodList[].purchaseUrl").description("구매 링크")
                        )))
                .when().get("/pet-foods")
                .then().extract();
        GetPetFoodsResponse data = response.body().as(GetPetFoodsResponse.class);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(data.petFoods()).isNotEmpty();
    }

    @Test
    void petFoods를_foodList로_직렬화한다() {
        //given
        ExtractableResponse<Response> response = given().contentType(ContentType.JSON)
                .when().get("/pet-foods")
                .then().extract();

        //when, then
        List<PetFoodResponse> foodList = response.jsonPath().getList("foodList", PetFoodResponse.class);
        assertThat(foodList).isNotEmpty();
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

        ErrorResponse data = response.body().as(ErrorResponse.class);

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(data.message()).isEqualTo("이름이 존재하지 않는 키워드 인 키워드를 찾을 수 없습니다.");
    }

}
