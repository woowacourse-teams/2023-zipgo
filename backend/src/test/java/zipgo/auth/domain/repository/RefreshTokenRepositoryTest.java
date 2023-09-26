package zipgo.auth.domain.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.auth.domain.RefreshToken;
import zipgo.common.repository.RepositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RefreshTokenRepositoryTest extends RepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void 토큰으로_찾을_수_있다() {
        // given
        refreshTokenRepository.save(new RefreshToken(1L, "집고의 비밀 토큰"));

        // when
        RefreshToken 토큰 = refreshTokenRepository.getByToken("집고의 비밀 토큰");

        // then
        assertAll(
                () -> assertThat(토큰.getId()).isEqualTo(1L),
                () -> assertThat(토큰.getMemberId()).isEqualTo(1L),
                () -> assertThat(토큰.getToken()).isEqualTo("집고의 비밀 토큰")
        );
    }

    @Test
    void 사용자_식별자로_삭제할_수_있다() {
        // given
        Long 사용자_식별자 = 1L;
        RefreshToken 집고의_저장된_토큰 = refreshTokenRepository.save(
                new RefreshToken(사용자_식별자, "집고의 비밀 토큰")
        );

        // when
        refreshTokenRepository.deleteByMemberId(사용자_식별자);

        // then
        Optional<RefreshToken> 옵셔널_리프레시_토큰 = refreshTokenRepository.findByToken(집고의_저장된_토큰.getToken());
        assertThat(옵셔널_리프레시_토큰).isEmpty();
    }

}
