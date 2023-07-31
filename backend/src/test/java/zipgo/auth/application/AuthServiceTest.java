package zipgo.auth.application;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipgo.auth.application.dto.KakaoOAuthResponse;
import zipgo.auth.application.dto.OAuthResponse;
import zipgo.auth.util.JwtProvider;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static zipgo.member.domain.fixture.MemberFixture.식별자_없는_멤버;
import static zipgo.member.domain.fixture.MemberFixture.식별자_있는_멤버;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceTest {

    @Mock
    private KakaoOAuthClient oAuthClient;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void 기존_멤버의_토큰을_발급한다() {
        // given
        카카오_토큰_받기_성공();

        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.of(식별자_있는_멤버()));
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
        when(memberRepository.findByEmail("이메일"))
                .thenReturn(Optional.empty());

        Member 멤버 = 식별자_없는_멤버();
        Member 저장된_멤버 = 식별자_있는_멤버();
        when(memberRepository.save(식별자_없는_멤버()))
                .thenReturn(저장된_멤버);

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
                .email("이메일")
                .profile(KakaoOAuthResponse.Profile.builder()
                        .nickname("이름")
                        .picture("사진")
                        .build())
                .build()).build();
    }

}
