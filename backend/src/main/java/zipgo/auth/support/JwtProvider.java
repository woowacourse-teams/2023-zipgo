package zipgo.auth.support;


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
import zipgo.auth.exception.TokenExpiredException;
import zipgo.auth.exception.TokenInvalidException;
import zipgo.common.config.JwtCredentials;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

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
