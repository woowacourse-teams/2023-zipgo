package zipgo.auth.application;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoDetailDto(
        String accessToken,
        String tokenType,
        String refreshToken,
        String expiresIn,
        String refreshTokenExpiresIn,
        String scope
) {

}
