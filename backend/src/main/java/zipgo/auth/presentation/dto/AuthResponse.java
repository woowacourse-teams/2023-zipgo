package zipgo.auth.presentation.dto;

import zipgo.member.domain.Member;

public record AuthResponse(
        String name,
        String profileImgUrl
) {

    public static AuthResponse from(Member member) {
        return new AuthResponse(
                member.getName(),
                member.getProfileImgUrl()
        );
    }

}