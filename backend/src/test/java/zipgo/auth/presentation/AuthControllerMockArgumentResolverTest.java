package zipgo.auth.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.Schema;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import zipgo.auth.presentation.dto.AuthCredentials;
import zipgo.member.application.MemberQueryService;
import zipgo.pet.application.PetQueryService;
import zipgo.pet.domain.fixture.PetFixture;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.resourceDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;
import static zipgo.pet.domain.fixture.BreedsFixture.견종;
import static zipgo.pet.domain.fixture.PetSizeFixture.대형견;


@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class AuthControllerMockArgumentResolverTest {

    private MockMvc mockMvc;
    private HandlerMethodArgumentResolver mockArgumentResolver = mock(JwtMandatoryArgumentResolver.class);

    @InjectMocks
    private AuthController authController;

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private PetQueryService petQueryService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new ExceptionHandlerExceptionResolver())
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .setCustomArgumentResolvers(mockArgumentResolver)
                .build();
    }

    @Test
    void 토큰을_받아_멤버_정보를_반환한다() throws Exception {
        // given
        when(mockArgumentResolver.supportsParameter(any()))
                .thenReturn(true);
        when(mockArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn(new AuthCredentials(1L));
        when(memberQueryService.findById(1L))
                .thenReturn(식별자_있는_멤버());
        when(petQueryService.readMemberPets(1L))
                .thenReturn(List.of(PetFixture.반려동물(식별자_있는_멤버(), 견종(대형견()))));

        // when
        var 요청 = mockMvc.perform(get("/auth")
                        .header(AUTHORIZATION, "Bearer 1a.2a.3b"))
                .andDo(문서_생성());

        // then
        요청.andExpect(status().isOk());
    }

    private RestDocumentationResultHandler 문서_생성() {
        var 응답_형식 = Schema.schema("AuthResponse");
        var 문서_정보 = resourceDetails().summary("사용자 정보 조회하기")
                .description("토큰이 가진 사용자 정보를 받습니다.")
                .responseSchema(응답_형식);

        return MockMvcRestDocumentationWrapper.document("사용자 정보 확인",
                문서_정보,
                requestHeaders(
                        headerWithName(AUTHORIZATION).description("Bearer + accessToken")
                ),
                responseFields(
                        fieldWithPath("name").description("사용자 이름").type(JsonFieldType.STRING),
                        fieldWithPath("profileImageUrl").description("사용자 프로필 사진").description("사용자 프로필 사진").type(JsonFieldType.STRING),
                        fieldWithPath("email").description("사용자 이메일").type(JsonFieldType.STRING),
                        fieldWithPath("hasPet").description("반려동물 등록 여부").type(JsonFieldType.BOOLEAN),
                        fieldWithPath("pets[].id").description("사용자 반려동물 식별자"),
                        fieldWithPath("pets[].name").description("사용자 반려동물 이름").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].age").description("사용자 반려동물 나이").type(JsonFieldType.NUMBER),
                        fieldWithPath("pets[].breed").description("사용자 반려동물 견종").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].breed").description("사용자 반려동물 견종").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].petSize").description("사용자 반려동물 견종 크기").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].gender").description("사용자 반려동물 견종 성별").type(JsonFieldType.STRING),
                        fieldWithPath("pets[].weight").description("사용자 반려동물 견종 몸무게").type(JsonFieldType.NUMBER),
                        fieldWithPath("pets[].imageUrl").description("사용자 반려동물 견종 사진").type(JsonFieldType.STRING)
                ));
    }

}

