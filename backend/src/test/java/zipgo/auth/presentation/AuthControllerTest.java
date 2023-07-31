package zipgo.auth.presentation;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zipgo.auth.application.AuthService;
import zipgo.auth.util.JwtProvider;
import zipgo.member.application.MemberQueryService;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;

@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = AuthController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private MemberQueryService memberQueryService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private JwtArgumentResolver jwtArgumentResolver;

    @Test
    void 소셜_로그인을_한다() throws Exception {
        // given
        when(authService.createToken("인가_코드"))
                .thenReturn("생성된_토큰");
        when(jwtProvider.getPayload("생성된_토큰"))
                .thenReturn("1");
        when(memberQueryService.findById(1L))
                .thenReturn(식별자_있는_멤버());

        // when
        var 요청 = mockMvc.perform(post("/auth/login")
                .param("code", "인가_코드"));

        // then
        var 응답 = 요청.andExpect(status().isOk());
    }

    private RestDocumentationFilter 소셜_로그인_문서_생성() {
        var 응답_형식 = Schema.schema("TokenResponse");
        var 문서_정보 = resourceDetails().summary("로그인 성공")
                .description("소셜 로그인을 시도합니다")
                .responseSchema(응답_형식);

        return RestAssuredRestDocumentationWrapper.document("소셜 로그인 성공",
                문서_정보,
                queryParameters(parameterWithName("keyword").optional().description("소셜 로그인 API")),
                responseFields(
                        fieldWithPath("tokenResponse").description("로그인 후 생성된 토큰 정보"),
                        fieldWithPath("tokenResponse[].accessToken").description("accessToken"),
                        fieldWithPath("tokenResponse[].authResponse[]").description("로그인 후 필요한 사용자 정보"),
                        fieldWithPath("tokenResponse[].authResponse[].name").description("사용자 이름"),
                        fieldWithPath("tokenResponse[].authResponse[].profileImgUrl").description("사용자 프로필 사진")

                ));
    }

}