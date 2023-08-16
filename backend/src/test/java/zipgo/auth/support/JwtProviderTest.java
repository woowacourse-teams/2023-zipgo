package zipgo.auth.support;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import zipgo.auth.exception.TokenExpiredException;
import zipgo.auth.exception.OAuthTokenNotBringException;
import zipgo.auth.exception.TokenInvalidException;
import zipgo.common.config.JwtCredentials;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JwtProviderTest {

    private static final String TEST_SECRET_KEY = "this1-is2-zipgo3-test4-secret5-key6";

    private final JwtProvider jwtProvider = new JwtProvider(new JwtCredentials(TEST_SECRET_KEY, 100000L));

    @Test
    void 토큰을_생성한다() {
        // given
        String 페이로드 = String.valueOf(1L);

        // expect
        String 토큰 = jwtProvider.create(페이로드);

        // then
        assertThat(토큰).isNotNull();
    }

    @Test
    void 올바른_토큰의_정보로_payload를_조회한다() {
        // given
        String 페이로드 = String.valueOf(1L);

        // when
        String 토큰 = jwtProvider.create(페이로드);

        // then
        assertThat(jwtProvider.getPayload(토큰)).isEqualTo(페이로드);
    }

    @Test
    void 유효하지_않은_토큰의_형식으로_payload를_조회할_경우_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> jwtProvider.getPayload("형식에맞지않는토큰"))
                .isInstanceOf(TokenInvalidException.class)
                .hasMessageContaining("잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

    @Test
    void 만료된_토큰으로_payload를_조회할_경우_예외가_발생한다() {
        // given
        Date 지나간_유효기간 = new Date((new Date()).getTime() - 1);
        String 만료된_토큰 = Jwts.builder()
                .signWith(hmacShaKeyFor(TEST_SECRET_KEY.getBytes(UTF_8)), HS256)
                .setSubject(String.valueOf(1L))
                .setExpiration(지나간_유효기간)
                .compact();

        // expect
        assertThatThrownBy(() -> jwtProvider.getPayload(만료된_토큰))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessageContaining("만료된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

    @Test
    void secretKey가_다른_토큰_정보로_payload_조회시_예외가_발생한다() {
        // given
        JwtProvider 다른_키의_jwt_provider = new JwtProvider(
                new JwtCredentials(
                        "빨주노초파남보나만의열쇠",
                        123123123123L
                )
        );

        // when
        String 다른_키로_만든_토큰 = 다른_키의_jwt_provider.create(String.valueOf(1L));

        // then
        assertThatThrownBy(() -> jwtProvider.getPayload(다른_키로_만든_토큰))
                .isInstanceOf(TokenInvalidException.class)
                .hasMessageContaining("잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

    @Test
    void 유효한_토큰인지_검증한다() {
        // given
        String 페이로드 = String.valueOf(1L);
        String 토큰 = jwtProvider.create(페이로드);

        // when
        assertDoesNotThrow(() -> jwtProvider.validateParseJws(토큰));
    }

    @Test
    void 만료된_토큰이라면_예외를_발생시킨다() {
        // given
        JwtProvider 유효기간이_지난_jwtProvider = new JwtProvider(
                new JwtCredentials(
                        TEST_SECRET_KEY,
                        -99999999999L
                )
        );
        String 페이로드 = String.valueOf(1L);
        String 토큰 = 유효기간이_지난_jwtProvider.create(페이로드);

        // expect
        assertThatThrownBy(() -> 유효기간이_지난_jwtProvider.validateParseJws(토큰))
                .isInstanceOf(TokenExpiredException.class)
                .hasMessageContaining("만료된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }

}
