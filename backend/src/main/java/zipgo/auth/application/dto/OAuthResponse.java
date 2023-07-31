package zipgo.auth.application.dto;

import zipgo.member.domain.Member;

public interface OAuthResponse {

    String getEmail();

    String getNickName();

    String getPicture();

    Member toMember();

}
