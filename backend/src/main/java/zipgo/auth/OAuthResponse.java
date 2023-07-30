package zipgo.auth;

import zipgo.member.domain.Member;

public interface OAuthResponse {

    String getEmail();

    String getNickName();

    String getPicture();

    Member createMember();

}
