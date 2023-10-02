package zipgo.auth.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.auth.domain.RefreshToken;
import zipgo.auth.exception.RefreshTokenNotFoundException;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    default RefreshToken getByToken(String token) {
        return findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    void deleteByMemberId(Long memberId);

}
