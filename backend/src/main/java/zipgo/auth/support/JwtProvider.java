package zipgo.auth.support;


import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import zipgo.auth.exception.AuthException;
import zipgo.common.config.JwtCredentials;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final long validityInMilliseconds;

    public JwtProvider(JwtCredentials jwtCredentials) {
        this.key = hmacShaKeyFor(jwtCredentials.getSecretKey().getBytes(UTF_8));
        this.validityInMilliseconds = jwtCredentials.getExpireLength();
    }

    public String create(String payload) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPayload(String token) {
        return validateParseJws(token).getBody().getSubject();
    }

    public Jws<Claims> validateParseJws(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (MalformedJwtException e) {
            throw new AuthException("JWT 서명이 잘못되었습니다.", e);
        } catch (ExpiredJwtException e) {
            throw new AuthException("유효기간이 만료된 토큰입니다.", e);
        } catch (SignatureException e) {
            throw new AuthException("토큰의 서명 유효성 검사가 실패했습니다.", e);
        } catch (Exception e) {
            throw new AuthException("토큰의 알 수 없는 문제가 발생했습니다.", e);
        }
    }

}
