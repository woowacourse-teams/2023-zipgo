package zipgo.auth.presentation.dto;

import zipgo.member.domain.Member;

public record TokenResponse (
        String accessToken,
        AuthResponse authResponse
) {


    public static TokenResponse of(String token, Member member) {
        return new TokenResponse(
                token,
                AuthResponse.from(member)
        );
    }

}
