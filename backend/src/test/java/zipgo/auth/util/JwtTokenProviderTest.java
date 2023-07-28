package zipgo.auth.util;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.*;
import static io.jsonwebtoken.security.Keys.*;
import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Test
    void 토큰을_생성한다() {
        // given
        String 페이로드 = String.valueOf(1L);

        // expect
        String 토큰 = jwtTokenProvider.create(페이로드);

        // then
        assertThat(토큰).isNotNull();
    }

    @DisplayName("올바른 토큰 정보로 payload를 조회한다.")
    @Test
    void 올바른_토큰의_정보로_payload를_조회한다() {
        // given
        String 페이로드 = String.valueOf(1L);

        // when
        String 토큰 = jwtTokenProvider.create(페이로드);

        // then
        assertThat(jwtTokenProvider.getPayload(토큰)).isEqualTo(페이로드);
    }

    @Test
    void 유효하지_않은_토큰의_형식으로_payload를_조회할_경우_예외가_발생한다() {
        // expect
        assertThatThrownBy(() -> jwtTokenProvider.getPayload(null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 만료된_토큰으로_payload를_조회할_경우_예외가_발생한다() {
        // given
        String 만료된_토큰 = Jwts.builder()
                .signWith(hmacShaKeyFor(secretKey.getBytes(UTF_8)), HS256)
                .setSubject(String.valueOf(1L))
                .setExpiration(new Date((new Date()).getTime() - 1))
                .compact();

        // expect
        assertThatThrownBy(() -> jwtTokenProvider.getPayload(만료된_토큰))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void secretKey가_다른_토큰_정보로_payload_조회시_예외가_발생한다() {
        // given
        JwtTokenProvider 다른_키의_jwt_provider = new JwtTokenProvider(
                "빨주노초파남보나만의열쇠",
                8640000L
        );

        // when
        String 다른_키로_만든_토큰 = 다른_키의_jwt_provider.create(String.valueOf(1L));

        // then
        assertThatThrownBy(() -> jwtTokenProvider.getPayload(다른_키로_만든_토큰))
                .isInstanceOf(IllegalStateException.class);
    }

}
