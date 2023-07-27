package zipgo.auth.application;

import zipgo.auth.OAuthResponse;

public interface OAuthClient {

    public String getAccessToken(String authCode);

    public OAuthResponse getMemberDetail(String accessToken);

}
