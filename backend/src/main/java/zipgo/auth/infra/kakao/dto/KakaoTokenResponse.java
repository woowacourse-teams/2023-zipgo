package zipgo.auth.infra.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokenResponse(
        String accessToken,
        String tokenType,
        String refreshToken,
        String expiresIn,
        String refreshTokenExpiresIn,
        String scope
) {

}
