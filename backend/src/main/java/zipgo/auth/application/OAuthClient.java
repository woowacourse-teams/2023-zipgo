package zipgo.auth.application;

import zipgo.auth.application.dto.OAuthMemberResponse;

public interface OAuthClient {

    String getAccessToken(String authCode, String redirectUri);

    OAuthMemberResponse getMember(String accessToken);

}
