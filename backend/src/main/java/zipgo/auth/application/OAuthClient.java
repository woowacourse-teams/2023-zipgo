package zipgo.auth.application;

import zipgo.auth.application.dto.OAuthMemberResponse;

public interface OAuthClient {

    public String getAccessToken(String authCode);

    public OAuthMemberResponse getMember(String accessToken);

}
