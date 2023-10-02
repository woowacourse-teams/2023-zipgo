package zipgo.auth.application;

import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.auth.domain.repository.RefreshTokenRepository;
import zipgo.auth.dto.TokenDto;
import zipgo.auth.infra.kakao.KakaoOAuthClient;
import zipgo.auth.infra.kakao.dto.KakaoMemberResponse;
import zipgo.auth.support.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static zipgo.member.domain.fixture.MemberFixture.식별자_없는_멤버;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceMockTest {

    @Mock
    private KakaoOAuthClient oAuthClient;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void 로그인에_성공하면_토큰을_발급한다() {
        // given
        카카오_토큰_받기_성공();
        Member 저장된_멤버 = 식별자_있는_멤버();
        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.of(저장된_멤버));
        when(jwtProvider.createAccessToken(저장된_멤버.getId()))
                .thenReturn("생성된 엑세스 토큰");
        when(jwtProvider.createRefreshToken())
                .thenReturn("생성된 리프레시 토큰");
        when(jwtProvider.createRefreshToken())
                .thenReturn("생성된 리프레시 토큰");

        // when
        TokenDto 토큰 = authService.login("코드");

        // then
        assertAll(
                () -> assertThat(토큰.accessToken()).isEqualTo("생성된 엑세스 토큰"),
                () -> assertThat(토큰.refreshToken()).isEqualTo("생성된 리프레시 토큰")
        );
    }

    @Test
    void 새로_가입한_회원의_토큰을_발급한다() {
        // given
        카카오_토큰_받기_성공();
        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.empty());
        when(memberRepository.save(식별자_없는_멤버()))
                .thenReturn(식별자_있는_멤버());
        when(jwtProvider.createAccessToken(1L))
                .thenReturn("생성된 토큰");

        // when
        TokenDto 토큰 = authService.login("코드");

        // then
        assertThat(토큰.accessToken()).isEqualTo("생성된 토큰");
    }

    private void 카카오_토큰_받기_성공() {
        when(oAuthClient.getAccessToken("코드"))
                .thenReturn("토큰");
        when(oAuthClient.getMember("토큰"))
                .thenReturn(카카오_응답());
    }

    private KakaoMemberResponse 카카오_응답() {
        return KakaoMemberResponse.builder().kakaoAccount(KakaoMemberResponse.KakaoAccount.builder()
                .email("이메일")
                .profile(KakaoMemberResponse.Profile.builder()
                        .nickname("이름")
                        .picture("사진")
                        .build())
                .build()).build();
    }

}
