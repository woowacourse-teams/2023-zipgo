package zipgo.auth.application;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.domain.RefreshToken;
import zipgo.auth.domain.repository.RefreshTokenRepository;
import zipgo.auth.dto.TokenDto;
import zipgo.auth.exception.TokenExpiredException;
import zipgo.auth.exception.TokenInvalidException;
import zipgo.auth.infra.kakao.dto.KakaoMemberResponse;
import zipgo.auth.support.JwtProvider;
import zipgo.common.config.JwtCredentials;
import zipgo.common.service.ServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuthServiceTest extends ServiceTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Test
    void 로그인시_기존_회원이라면_토큰만_발급한다() {
        // given
        memberRepository.save(Member.builder().email("이메일").name("이름").build());

        // when
        TokenDto 생성된_토큰 = authService.login(외부_인프라_사용자_응답_생성());

        // then
        assertThat(생성된_토큰).isNotNull();
    }

    @Test
    void 로그인시_새_회원은_가입_후_토큰을_발급한다() {
        // given
        OAuthMemberResponse 외부_인프라_사용자_응답 = 외부_인프라_사용자_응답_생성();

        // when
        TokenDto 생성된_토큰 = authService.login(외부_인프라_사용자_응답);

        // then
        assertAll(
                () -> assertThat(memberRepository.findByEmail(외부_인프라_사용자_응답.getEmail())).isNotEmpty(),
                () -> assertThat(생성된_토큰).isNotNull()
        );
    }

    @Test
    void 토큰_갱신시_리프레시_토큰을_받아_엑세스_토큰을_발급한다() {
        // given
        Long 사용자_식별자 = 1L;
        String 리프레시_토큰 = jwtProvider.createRefreshToken();
        refreshTokenRepository.save(new RefreshToken(사용자_식별자, 리프레시_토큰));

        // when
        String 엑세스_토큰 = authService.renewAccessTokenBy(리프레시_토큰);

        // then
        String 페이로드 = jwtProvider.getPayload(엑세스_토큰);
        assertThat(페이로드).isEqualTo("1");
    }

    @Test
    void 토큰_갱신시_리프레시_토큰이_유효하지_않다면_에외가_발생한다() {
        // expect
        assertThatThrownBy(() -> authService.renewAccessTokenBy("검증되지 않은 토큰"))
                .isInstanceOf(TokenInvalidException.class)
                .hasMessageContaining("잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요");
    }

    @Test
    void 토큰_갱신시_리프레시_토큰이_만료됐다면_에외가_발생한다() {
        // given
        JwtProvider 만료된_토큰_생성기 = new JwtProvider(new JwtCredentials(
                "this1-is2-zipgo3-test4-secret5-key6",
                -9999,
                -9999
        ));
        String 만료된_토큰 = 만료된_토큰_생성기.createRefreshToken();

        // expect
        assertThatThrownBy(() -> authService.renewAccessTokenBy(만료된_토큰))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessageContaining("만료된 토큰입니다. 올바른 토큰으로 다시 시도해주세요");
    }

    @Test
    void 로그아웃시_저장된_토큰이_사라진다() {
        // given
        Long memberId = 1L;
        refreshTokenRepository.save(new RefreshToken(memberId, "저장시킨 토큰"));

        // when
        authService.logout(memberId);

        // then
        assertThat(refreshTokenRepository.findByToken("저장시킨 토큰")).isEmpty();
    }

    private OAuthMemberResponse 외부_인프라_사용자_응답_생성() {
        return KakaoMemberResponse.builder().kakaoAccount(KakaoMemberResponse.KakaoAccount.builder()
                .email("이메일")
                .profile(KakaoMemberResponse.Profile.builder()
                        .nickname("이름")
                        .picture("사진")
                        .build())
                .build()).build();
    }

}
