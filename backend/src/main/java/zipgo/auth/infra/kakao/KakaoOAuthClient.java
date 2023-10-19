package zipgo.auth.infra.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import zipgo.auth.application.dto.OAuthMemberResponse;
import zipgo.auth.domain.OAuthClient;
import zipgo.auth.domain.OAuthMemberInfoClient;
import zipgo.auth.domain.OAuthTokenClient;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private final OAuthTokenClient kakaoOAuthTokenClient;
    private final OAuthMemberInfoClient kakaoOAuthMemberInfoClient;

    @Override
    public OAuthMemberResponse request(String authCode, String redirectUri) {
        String accessToken = kakaoOAuthTokenClient.getAccessToken(authCode, redirectUri);
        return kakaoOAuthMemberInfoClient.getMember(accessToken);
    }

}
