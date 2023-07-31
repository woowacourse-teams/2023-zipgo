package zipgo.auth.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import zipgo.auth.presentation.dto.AuthDto;
import zipgo.member.application.MemberQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthControllerMockArgumentResolverTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private MemberQueryService memberQueryService;

    private HandlerMethodArgumentResolver mockArgumentResolver = mock(JwtArgumentResolver.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new ExceptionHandlerExceptionResolver())
                .setCustomArgumentResolvers(mockArgumentResolver)
                .build();
    }

    @Test
    void 토큰을_받아_멤버_정보를_반환한다() throws Exception {
        // given
        when(mockArgumentResolver.supportsParameter(any()))
                .thenReturn(true);
        when(mockArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn(new AuthDto(1L));
        when(memberQueryService.findById(1L))
                .thenReturn(식별자_있는_멤버());

        // when
        var 요청 = mockMvc.perform(get("/auth")
                .header(AUTHORIZATION, "Bearer 1a.2a.3b"));

        // then
        var 응답 = 요청.andExpect(status().isOk());
    }

}
