package zipgo.auth.dto;

import java.util.List;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Pet;

public record TokenResponse(
        String accessToken,
        AuthResponse authResponse
) {

    public static TokenResponse of(String token, Member member, List<Pet> pets) {
        return new TokenResponse(
                token,
                AuthResponse.of(member, pets)
        );
    }

}
