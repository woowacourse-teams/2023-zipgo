package zipgo.pet.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.acceptance.AcceptanceTest;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.pet.presentation.dto.request.CreatePetRequest;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static zipgo.pet.domain.fixture.BreedsFixture.품종_생성;
import static zipgo.pet.domain.fixture.PetSizeFixture.대형견_생성;
import static zipgo.review.fixture.MemberFixture.멤버_이름;

class PetControllerTest extends AcceptanceTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(멤버_이름("갈비"));
        PetSize 대형견 = petSizeRepository.save(대형견_생성());
        breedsRepository.save(품종_생성("시베리안 허스키", 대형견));
    }

    @Test
    void 반려동물을_성공적으로_생성하면_201_반환() {
        // given
        var token = jwtProvider.create("1");
        var 반려견_생성_요청 = new CreatePetRequest("상근이", "남", "아기사진", 3, "시베리안 허스키", "대형견", 57.8);
        var 요청_준비 = given(spec).header("Authorization", "Bearer " + token).body(반려견_생성_요청)
                .contentType(JSON).filter(성공_API_문서_생성());

        // when
        var 응답 = 요청_준비.when().post("/pets");

        // then
        응답.then().assertThat().statusCode(CREATED.value());
    }

    private RestDocumentationFilter 성공_API_문서_생성() {
        var 문서_정보 = resourceDetails().summary("반려견 등록").description("반려견을 등록합니다.");
        return document("반려견 등록 - 성공",
                문서_정보,
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT"))
                , requestFields(
                        fieldWithPath("name").description("반려견 이름").type(JsonFieldType.STRING),
                        fieldWithPath("age").description("반려견 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("image").description("이미지 링크").optional().type(JsonFieldType.STRING),
                        fieldWithPath("breed").description("반려견 종").optional().type(JsonFieldType.STRING),
                        fieldWithPath("petSize").description("반려견 크기(소/중/대)").type(JsonFieldType.STRING),
                        fieldWithPath("gender").description("반려견 성별").type(JsonFieldType.STRING),
                        fieldWithPath("weight").description("반려견 몸무게").type(JsonFieldType.NUMBER))
        );
    }

}
