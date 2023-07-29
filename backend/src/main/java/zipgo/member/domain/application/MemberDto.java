package zipgo.member.domain.application;

import zipgo.auth.OAuthResponse;

public record MemberDto(
        String name,
        String profileImgUrl,
        String email
) {

    public static MemberDto from(OAuthResponse oAuthResponse) {
        return new MemberDto(
                oAuthResponse.getNickName(),
                oAuthResponse.getPicture(),
                oAuthResponse.getEmail()
        );
    }

}
