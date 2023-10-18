package zipgo.auth.domain;

import zipgo.auth.application.dto.OAuthMemberResponse;

public interface OAuthClient {

    OAuthMemberResponse request(String authCode, String redirectUri);

}
