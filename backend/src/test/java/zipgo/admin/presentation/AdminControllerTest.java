package zipgo.admin.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.brand.domain.fixture.BrandFixture;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.common.acceptance.AcceptanceTest;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

class AdminControllerTest extends AcceptanceTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void 브랜드_조회_하면_200반환() {
        //given
        brandRepository.save(BrandFixture.아카나_식품_브랜드_생성());
        brandRepository.save(BrandFixture.인스팅트_식품_브랜드_생성());
        var 요청_준비 = given(spec)
                .contentType(JSON)
                .filter(브랜드_조회_API_문서_생성());

        //when
        var 응답 = 요청_준비.when()
                .get("/admin/brands");

        //then
        응답.then().log().all()
                .assertThat().statusCode(OK.value())
                .assertThat().body("size()", equalTo(2));
    }

    private RestDocumentationFilter 브랜드_조회_API_문서_생성() {
        var 문서_정보 = resourceDetails().summary("브랜드 조회").description("브랜드를 조회합니다.");
        return document("브랜드 조회 - 성공", 문서_정보,
                responseFields(
                        fieldWithPath("[].id").description("브랜드 id").type(JsonFieldType.NUMBER),
                        fieldWithPath("[].name").description("브랜드 이름").type(JsonFieldType.STRING)
                ));
    }

}
