package zipgo.auth.application;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.auth.domain.RefreshToken;
import zipgo.auth.domain.repository.RefreshTokenRepository;
import zipgo.auth.exception.TokenInvalidException;
import zipgo.auth.support.JwtProvider;
import zipgo.common.service.ServiceTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest extends ServiceTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthService authService;

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
    void 로그아웃시_저장된_토큰이_사라진다() {
        // given
        Long memberId = 1L;
        refreshTokenRepository.save(new RefreshToken(memberId, "저장시킨 토큰"));

        // when
        authService.logout(memberId);

        // then
        assertThat(refreshTokenRepository.findByToken("저장시킨 토큰")).isEmpty();
    }

}
