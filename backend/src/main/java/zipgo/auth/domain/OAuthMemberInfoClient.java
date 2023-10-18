package zipgo.auth.domain;

import zipgo.auth.application.dto.OAuthMemberResponse;

public interface OAuthMemberInfoClient {

    OAuthMemberResponse getMember(String accessToken);

}
