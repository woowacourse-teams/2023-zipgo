package zipgo.petfood.presentation;

import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.common.acceptance.AcceptanceTest;
import zipgo.petfood.domain.fixture.PrimaryIngredientFixture;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static com.epages.restdocs.apispec.Schema.schema;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

class PrimaryIngredientControllerTest extends AcceptanceTest {

    @Autowired
    private ObjectMapper objectMapper;

    private ResourceSnippetDetails 문서_정보 = resourceDetails().summary("주원료 생성하기")
            .description("사료에 대한 주원료 카테고리를 생성합니다.");
    private Schema 요청_형식 = schema("CreatePrimaryIngredientRequest");
    private Schema 성공_응답_형식 = schema("CreatePrimaryIngredientResponse");

    @Test
    void createFunctionality() throws JsonProcessingException {
        // given
        var 닭고기_주원료_요청 = PrimaryIngredientFixture.닭고기_주원료_요청;
        var content = objectMapper.writeValueAsString(닭고기_주원료_요청);
        var 요청_준비 = given(spec)
                .body(content)
                .contentType(JSON)
                .filter(주원료_생성_API_문서_생성());

        // when
        var 응답 = 요청_준비.when().log().all().post("/primary-ingredients");

        // then
        응답.then().assertThat().statusCode(CREATED.value());
    }

    private RestDocumentationFilter 주원료_생성_API_문서_생성() {
        return document("주원료 생성 - 성공", 문서_정보.requestSchema(요청_형식).responseSchema(성공_응답_형식),
                requestFields(fieldWithPath("name").description("주원료 이름").type(JsonFieldType.STRING)));
    }



}
