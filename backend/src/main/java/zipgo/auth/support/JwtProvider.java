package zipgo.auth.support;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import zipgo.auth.exception.TokenExpiredException;
import zipgo.auth.exception.TokenInvalidException;
import zipgo.common.config.JwtCredentials;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final long accessTokenExpireLength;
    private final long refreshTokenExpireLength;

    public JwtProvider(JwtCredentials jwtCredentials) {
        this.key = hmacShaKeyFor(jwtCredentials.getSecretKey().getBytes(UTF_8));
        this.accessTokenExpireLength = jwtCredentials.getAccessTokenExpireLength();
        this.refreshTokenExpireLength = jwtCredentials.getRefreshTokenExpireLength();
    }

    public String createAccessToken(String payload) {
        return createToken(payload, accessTokenExpireLength, key);
    }

    private String createToken(String payload, long expireLength, SecretKey key) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireLength);
        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        return createToken(UUID.randomUUID().toString(), refreshTokenExpireLength, key);
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
            throw new TokenInvalidException();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        } catch (SignatureException e) {
            throw new TokenInvalidException();
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

}
