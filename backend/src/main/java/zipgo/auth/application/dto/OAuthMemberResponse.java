package zipgo.auth.application.dto;

import zipgo.member.domain.Member;

public interface OAuthMemberResponse {

    String getEmail();

    String getNickName();

    String getPicture();

    Member toMember();

}
