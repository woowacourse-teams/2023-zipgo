package zipgo.auth.application;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.auth.KakaoOAuthResponse;
import zipgo.auth.OAuthResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.application.MemberCommandService;
import zipgo.member.domain.application.MemberDto;
import zipgo.member.domain.application.MemberQueryService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private static final Member MEMBER_FIXTURE = Member.builder().id(1L).email("zipgo@zipgo.com").name("개비").profileImgUrl("사진").build();

    @Mock
    private KakaoOAuthClient oAuthClient;

    @Mock
    private MemberCommandService memberCommandService;

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void 기존_멤버의_토큰을_발급한다() {
        // given
        카카오_토큰_받기_성공();

        when(memberCommandService.findByEmail("zipgo@zipgo.com"))
                .thenReturn(Optional.of(MEMBER_FIXTURE));
        when(jwtProvider.create("1"))
                .thenReturn("생성된 토큰");

        // when
        String 토큰 = authService.createToken("코드");

        // then
        assertThat(토큰).isEqualTo("생성된 토큰");
    }

    @Test
    void 새로_가입한_멤버의_토큰을_발급한다() {
        // given
        OAuthResponse 카카오_응답 = 카카오_토큰_받기_성공();

        when(memberCommandService.findByEmail("zipgo@zipgo.com"))
                .thenReturn(Optional.empty());

        MemberDto 멤버_DTO = new MemberDto("개비", "사진", "zipgo@zipgo.com");
        when(memberQueryService.save(멤버_DTO))
                .thenReturn(MEMBER_FIXTURE);

        when(jwtProvider.create("1"))
                .thenReturn("생성된 토큰");

        // when
        String 토큰 = authService.createToken("코드");

        // then
        assertThat(토큰).isEqualTo("생성된 토큰");
    }

    private OAuthResponse 카카오_토큰_받기_성공() {
        when(oAuthClient.getAccessToken("코드"))
                .thenReturn("토큰");

        OAuthResponse oAuthResponse = 카카오_응답();
        when(oAuthClient.getMemberDetail("토큰"))
                .thenReturn(oAuthResponse);

        return oAuthResponse;
    }

    private static KakaoOAuthResponse 카카오_응답() {
        return KakaoOAuthResponse.builder().kakaoAccount(KakaoOAuthResponse.KakaoAccount.builder()
                .email("zipgo@zipgo.com")
                .profile(KakaoOAuthResponse.Profile.builder()
                        .nickname("개비")
                        .picture("사진")
                        .build())
                .build()).build();
    }

}
