package zipgo.pet.presentation;

import java.time.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import zipgo.common.acceptance.AcceptanceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.pet.presentation.dto.request.CreatePetRequest;
import zipgo.pet.presentation.dto.request.UpdatePetRequest;
import zipgo.review.fixture.MemberFixture;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static zipgo.pet.domain.Gender.MALE;
import static zipgo.pet.domain.fixture.BreedsFixture.견종_생성;
import static zipgo.pet.domain.fixture.PetSizeFixture.대형견;
import static zipgo.pet.domain.fixture.PetSizeFixture.소형견;
import static zipgo.review.fixture.MemberFixture.멤버_이름;

class PetControllerTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private PetRepository petRepository;

    private PetSize 대형견;
    private Breeds 시베리안_허스키;
    private Member 갈비;

    @BeforeEach
    void setUp() {
        대형견 = petSizeRepository.save(대형견());
        시베리안_허스키 = breedsRepository.save(견종_생성("시베리안 허스키", 대형견));
        갈비 = memberRepository.save(멤버_이름("갈비"));
    }

    @Nested
    class 반려동물_등록시 {

        @Test
        void 성공하면_201_반환한다_허스키() {
            // given
            var token = jwtProvider.create("1");
            var 반려견_생성_요청 = new CreatePetRequest("상근이", "남", "아기사진", 3, "시베리안 허스키", "", 57.8);
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token).body(반려견_생성_요청)
                    .contentType(JSON).filter(반려동물_등록_성공_API_문서_생성());

            // when
            var 응답 = 요청_준비.when().post("/pets");

            // then
            응답.then().statusCode(CREATED.value());
        }

        @Test
        void 성공하면_201_반환한다_믹스견() {
            // given
            대형견 = petSizeRepository.save(대형견());
            var 소형견 = petSizeRepository.save(소형견());
            breedsRepository.save(견종_생성("믹스견", 대형견));
            breedsRepository.save(견종_생성("믹스견", 소형견));
            var token = jwtProvider.create("1");
            var 반려견_생성_요청 = new CreatePetRequest("나만의소중한", "남", "아기사진", 3, "믹스견", "소형견", 57.8);
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token).body(반려견_생성_요청)
                    .contentType(JSON).filter(반려동물_등록_성공_API_문서_생성());

            // when
            var 응답 = 요청_준비.when().post("/pets");

            // then
            응답.then().statusCode(CREATED.value());
        }

        @Test
        void 존재하지_않는_견종이면_404_반환한다() {
            var token = jwtProvider.create("1");
            var 반려견_생성_요청 = new CreatePetRequest("상근이", "남", "아기사진", 3, "존재하지 않는 종", "대형견", 57.8);
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token).body(반려견_생성_요청)
                    .contentType(JSON).filter(API_반려동물_등록_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().post("/pets");

            // then
            응답.then().statusCode(NOT_FOUND.value());
        }

        @Test
        void 존재하지_않는_견종_크기면_404_반환한다() {
            var token = jwtProvider.create("1");
            var 반려견_생성_요청 = new CreatePetRequest("상근이", "남", "아기사진", 3, "시베리안 허스키", "초초초 대형견", 57.8);
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + token).body(반려견_생성_요청)
                    .contentType(JSON).filter(API_반려동물_등록_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().post("/pets");

            // then
            응답.then().statusCode(NOT_FOUND.value());
        }

    }

    @Nested
    class 반려동물_정보_수정시 {

        @Test
        void 성공하면_204_반환한다() {
            // given
            var 쫑이 = 반려견_생성();
            var 토큰 = jwtProvider.create("1");
            var 반려견_수정_요청 = new UpdatePetRequest("상근이", "남", "아기사진", 3, "시베리안 허스키", "대형견", 57.8);
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + 토큰).body(반려견_수정_요청)
                    .contentType(JSON).filter(반려동물_정보_수정_API_성공());

            // when
            var 응답 = 요청_준비.when().put("/pets/{petId}", 쫑이.getId());

            // then
            응답.then().statusCode(NO_CONTENT.value());
        }

        @Test
        void 반려견과_주인이_맞지_않으면_404_반환한다() {
            // given
            var 쫑이 = 반려견_생성();
            var 토큰 = jwtProvider.create("2");
            var 반려견_수정_요청 = new UpdatePetRequest("상근이", "남", "아기사진", 3, "시베리안 허스키", "대형견", 57.8);
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + 토큰).body(반려견_수정_요청)
                    .contentType(JSON).filter(API_반려동물_수정_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().put("/pets/{petId}", 쫑이.getId());

            // then
            응답.then().statusCode(NOT_FOUND.value());
        }

        @Test
        void 존재하지_않는_petId로_요청시_404_반환한다() {
            // given
            var 존재하지_않는_petId = 999999L;
            var 토큰 = jwtProvider.create("1");
            var 반려견_수정_요청 = new UpdatePetRequest("상근이", "남", "아기사진", 3, "시베리안 허스키", "대형견", 57.8);
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + 토큰).body(반려견_수정_요청)
                    .contentType(JSON).filter(API_반려동물_수정_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().put("/pets/{petId}", 존재하지_않는_petId);

            // then
            응답.then().statusCode(NOT_FOUND.value());
        }

    }

    @Nested
    class 반려동물_삭제시 {

        @Test
        void 성공하면_204를_반환한다() {
            // given
            var 쫑이 = 반려견_생성();
            var 토큰 = jwtProvider.create(String.valueOf(갈비.getId()));
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + 토큰)
                    .contentType(JSON).filter(반려동물_삭제_API_성공());

            // when
            var 응답 = 요청_준비.when().delete("/pets/{petId}", 쫑이.getId());

            // then
            응답.then().statusCode(NO_CONTENT.value());
        }

        @Test
        void 반려견_주인이_일치하지_않으면_예외가_발생한다() {
            // given
            var 쫑이 = 반려견_생성();
            Member 주인_아닌_사람 = memberRepository.save(MemberFixture.무민());
            var 토큰 = jwtProvider.create(String.valueOf(주인_아닌_사람.getId()));
            var 요청_준비 = given(spec).header("Authorization", "Bearer " + 토큰)
                    .contentType(JSON).filter(API_반려동물_삭제_예외응답_문서_생성());

            // when
            var 응답 = 요청_준비.when().put("/pets/{petId}", 쫑이.getId());

            // then
            응답.then().statusCode(BAD_REQUEST.value());
        }

    }

    @Test
    void 견종_목록_조회_성공하면_200_반환한다() {
        // given
        var 요청_준비 = given(spec)
                .contentType(JSON).filter(견종_목록_조회_API_성공());

        // when
        var 응답 = 요청_준비.when()
                .get("/pets/breeds");

        // then
        응답.then().statusCode(OK.value());
    }

    @Test
    void 사용자_반려동물_조회_성공시_200_반환한다() {
        // given
        var 갈비 = petRepository.save(반려견_생성());
        var 토큰 = jwtProvider.create(String.valueOf(갈비.getOwner().getId()));

        var 요청_준비 = given(spec)
                .header("Authorization", "Bearer " + 토큰)
                .contentType(JSON).filter(사용자_반려동물_조회_API_성공());

        // when
        var 응답 = 요청_준비.when()
                .get("/pets");

        // then
        응답.then().statusCode(OK.value());
    }

    @Test
    void 반려동물_정보_조회_성공시_200_반환한다() {
        // given
        Pet 반려견 = petRepository.save(반려견_생성());
        var 요청_준비 = given(spec)
                .contentType(JSON).filter(반려동물_조회_API_성공());

        // when
        var 응답 = 요청_준비.when()
                .get("/pets/{petId}", 반려견.getId());

        // then
        응답.then().statusCode(OK.value());
    }


    private RestDocumentationFilter 반려동물_등록_성공_API_문서_생성() {
        return document("반려동물 등록 - 성공", resourceDetails().summary("반려동물 등록하기").description("반려동물을 등록합니다."),
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                requestFields(
                        fieldWithPath("name").description("반려견 이름").type(JsonFieldType.STRING),
                        fieldWithPath("age").description("반려견 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("imageUrl").description("이미지 링크").optional().type(JsonFieldType.STRING),
                        fieldWithPath("breed").description("견종").optional().type(JsonFieldType.STRING),
                        fieldWithPath("petSize").description("반려견 크기(소/중/대)").type(JsonFieldType.STRING),
                        fieldWithPath("gender").description("반려견 성별").type(JsonFieldType.STRING),
                        fieldWithPath("weight").description("반려견 몸무게").type(JsonFieldType.NUMBER))
        );
    }

    private RestDocumentationFilter 반려동물_정보_수정_API_성공() {
        return document("반려동물 정보 수정 - 성공", resourceDetails().summary("반려동물 수정하기").description("반려동물을 정보를 수정합니다."),
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                pathParameters(parameterWithName("petId").description("반려견 식별자")),
                requestFields(
                        fieldWithPath("name").description("반려견 이름").type(JsonFieldType.STRING),
                        fieldWithPath("age").description("반려견 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("imageUrl").description("이미지 링크").optional().type(JsonFieldType.STRING),
                        fieldWithPath("breed").description("견 종").optional().type(JsonFieldType.STRING),
                        fieldWithPath("petSize").description("반려견 크기(소/중/대)").type(JsonFieldType.STRING),
                        fieldWithPath("gender").description("반려견 성별").type(JsonFieldType.STRING),
                        fieldWithPath("weight").description("반려견 몸무게").type(JsonFieldType.NUMBER))
        );
    }

    private RestDocumentationFilter 반려동물_삭제_API_성공() {
        return document("반려동물 삭제 - 성공", resourceDetails().summary("반려동물 삭제하기").description("반려동물을 삭제합니다."),
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")),
                pathParameters(parameterWithName("petId").description("반려견 식별자"))
        );
    }

    private RestDocumentationFilter 견종_목록_조회_API_성공() {
        return document("견종 목록 조회 - 성공", resourceDetails().summary("견종 목록 조회").description("견종 목록을 조회합니다."),
                responseFields(
                        fieldWithPath("breeds").description("견종").type(JsonFieldType.ARRAY),
                        fieldWithPath("breeds[].id").description("견종 식별자").type(JsonFieldType.NUMBER),
                        fieldWithPath("breeds[].name").description("견종 이름").type(JsonFieldType.STRING)
                ));
    }

    private RestDocumentationFilter 사용자_반려동물_조회_API_성공() {
        return document("사용자 반려견 조회 - 성공", resourceDetails().summary("사용자의 반려견 조회").description("사용자의 반려견을 조회합니다."),
                responseFields(
                        fieldWithPath("pets[]").description("반려견 목록").type(JsonFieldType.ARRAY),
                        fieldWithPath("pets[].id").description("반려견 식별자").type(JsonFieldType.NUMBER),
                        fieldWithPath("pets[].name").description("반려견 이름").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].age").description("반려견 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("pets[].breed").description("반려견 견종").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].petSize").description("반려견 크기").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].gender").description("반려견 성별").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].weight").description("반려견 몸무게").type(JsonFieldType.NUMBER),
                        fieldWithPath("pets[].imageUrl").description("반려견 사진 주소").type(JsonFieldType.STRING)
                ));
    }

    private RestDocumentationFilter 반려동물_조회_API_성공() {
        return document("반려견 정보 조회 - 성공", resourceDetails().summary("반려견 정보 조회").description("반려견의 정보를 조회합니다."),
                responseFields(
                        fieldWithPath("id").description("반려견 식별자").type(JsonFieldType.NUMBER),
                        fieldWithPath("name").description("반려견 이름").type(JsonFieldType.STRING),
                        fieldWithPath("age").description("반려견 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("breed").description("반려견 견종").type(JsonFieldType.STRING),
                        fieldWithPath("petSize").description("반려견 크기").type(JsonFieldType.STRING),
                        fieldWithPath("gender").description("반려견 성별").type(JsonFieldType.STRING),
                        fieldWithPath("weight").description("반려견 몸무게").type(JsonFieldType.NUMBER),
                        fieldWithPath("imageUrl").description("반려견 사진 주소").type(JsonFieldType.STRING)
                ));
    }

    private RestDocumentationFilter API_반려동물_등록_예외응답_문서_생성() {
        return document("반려견 등록 - 실패(없는 견종)",
                resourceDetails().summary("반려동물 등록하기").description("반려동물을 등록합니다.").responseSchema(에러_응답_형식),
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")));
    }

    private RestDocumentationFilter API_반려동물_수정_예외응답_문서_생성() {
        return document("반려견 수정 - 실패(반려견 주인과 사용자 불일치)",
                resourceDetails().summary("반려동물 수정하기").description("반려동물을 정보를 수정합니다.").responseSchema(에러_응답_형식),
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")));
    }

    private RestDocumentationFilter API_반려동물_삭제_예외응답_문서_생성() {
        return document("반려견 수정 - 실패(반려견 주인과 사용자 불일치)",
                resourceDetails().summary("반려동물 삭제하기").description("반려동물 삭제합니다.").responseSchema(에러_응답_형식),
                requestHeaders(headerWithName("Authorization").description("인증을 위한 JWT")));
    }

    private Pet 반려견_생성() {
        Pet 쫑이 = Pet.builder()
                .name("갈비")
                .owner(갈비)
                .gender(MALE)
                .breeds(시베리안_허스키)
                .birthYear(Year.of(2005))
                .imageUrl("갈비_사진")
                .weight(35.5)
                .build();
        return petRepository.save(쫑이);
    }

}
