package zipgo.auth.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetDetails;
import com.epages.restdocs.apispec.Schema;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zipgo.auth.application.AuthService;
import zipgo.auth.exception.OAuthTokenNotBringException;
import zipgo.auth.support.JwtProvider;
import zipgo.member.application.MemberQueryService;
import zipgo.pet.application.PetQueryService;
import zipgo.pet.domain.fixture.PetFixture;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static java.util.Collections.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetFixture.반려동물_생성;
import static zipgo.pet.domain.fixture.PetSizeFixture.대형견;

@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(controllers = AuthController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthControllerTest {

    private static final Schema 응답_형식 = Schema.schema("TokenResponse");
    private static final ResourceSnippetDetails 문서_정보 = resourceDetails().summary("로그인")
                .description("로그인 합니다.")
                .responseSchema(응답_형식);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberQueryService memberQueryService;

    @MockBean
    private PetQueryService petQueryService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private AuthService authService;

    @Test
    void 로그인_성공() throws Exception {
        // given
        when(authService.createToken("인가_코드"))
                .thenReturn("생성된_토큰");
        when(jwtProvider.getPayload("생성된_토큰"))
                .thenReturn("1");
        when(memberQueryService.findById(1L))
                .thenReturn(식별자_있는_멤버());
        when(petQueryService.readMemberPets(1L))
                .thenReturn(List.of(PetFixture.반려동물_생성()));

        // when
        var 요청 = mockMvc.perform(post("/auth/login")
                        .param("code", "인가_코드"))
                .andDo(로그인_성공_문서_생성());

        // then
        요청.andExpect(status().isOk());
    }

    @Test
    void 로그인_성공_후_사용자의_반려동물이_없을시_petProfile은_반환되지_않는다() throws Exception {
        // given
        when(authService.createToken("인가_코드"))
                .thenReturn("생성된_토큰");
        when(jwtProvider.getPayload("생성된_토큰"))
                .thenReturn("1");
        when(memberQueryService.findById(1L))
                .thenReturn(식별자_있는_멤버());
        when(petQueryService.readMemberPets(1L))
                .thenReturn(EMPTY_LIST);

        // when
        var 요청 = mockMvc.perform(post("/auth/login")
                        .param("code", "인가_코드"))
                .andDo(로그인_성공_반려동물_정보_없음_문서_생성());

        // then
        요청.andExpect(status().isOk());
    }

    @Test
    void 자원_서버의_토큰을_가져오는데_실패하면_예외가_발생한다() throws Exception {
        // given
        when(authService.createToken("인가_코드"))
                .thenThrow(new OAuthTokenNotBringException());

        // when
        var 요청 = mockMvc.perform(post("/auth/login")
                .param("code", "인가_코드"));

        // then
        요청.andExpect(status().isBadGateway());
    }

    private RestDocumentationResultHandler 로그인_성공_문서_생성() {
        return MockMvcRestDocumentationWrapper.document("로그인 성공 - 반려동물 기등록",
                문서_정보,
                queryParameters(
                        parameterWithName("code").optional().description("로그인 API")
                ),
                responseFields(
                        fieldWithPath("accessToken").description("accessToken").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.name").description("사용자 이름").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.email").description("사용자 이메일").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.profileImageUrl").description("사용자 프로필 사진").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.hasPet").description("반려동물 등록 여부").type(JsonFieldType.BOOLEAN),
                        fieldWithPath("authResponse.pets[].id").description("반려동물 식별자"),
                        fieldWithPath("authResponse.pets[].name").description("반려동물 이름").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].age").description("반려동물 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("authResponse.pets[].breed").description("반려동물 견종").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].petSize").description("반려동물 크기").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].gender").description("반려동물 성별").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.pets[].weight").description("반려동물 몸무게").type(JsonFieldType.NUMBER),
                        fieldWithPath("authResponse.pets[].imageUrl").description("반려동물 사진 주소").type(JsonFieldType.STRING)
                ));
    }

    private RestDocumentationResultHandler 로그인_성공_반려동물_정보_없음_문서_생성() {
        return MockMvcRestDocumentationWrapper.document("로그인 성공 - 반려동물 미등록",
                문서_정보,
                queryParameters(
                        parameterWithName("code").optional().description("로그인 API")
                ),
                responseFields(
                        fieldWithPath("accessToken").description("accessToken").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.name").description("사용자 이름").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.email").description("사용자 이메일").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.profileImageUrl").description("사용자 프로필 사진").type(JsonFieldType.STRING),
                        fieldWithPath("authResponse.hasPet").description("반려동물 등록 여부").type(JsonFieldType.BOOLEAN),
                        fieldWithPath("authResponse.pets").description("반려동물 프로필").type(JsonFieldType.ARRAY)
                ));
    }

}
