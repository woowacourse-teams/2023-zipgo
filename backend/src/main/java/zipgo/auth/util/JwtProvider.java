package zipgo.auth.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zipgo.auth.exception.AuthException;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final long validityInMilliseconds;

    public JwtProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.expire-length}") long validityInMilliseconds
    ) {
        this.key = hmacShaKeyFor(secretKey.getBytes(UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
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
        return tokenToJws(token).getBody().getSubject();
    }

    public void validateAbleToken(String token) {
        tokenToJws(token);
    }

    private Jws<Claims> tokenToJws(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (IllegalArgumentException | MalformedJwtException | ExpiredJwtException | SignatureException | UnsupportedJwtException e) {
            throw new AuthException(e);
        }
    }
}
