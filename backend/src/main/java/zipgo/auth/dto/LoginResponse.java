package zipgo.auth.dto;

import java.util.List;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Pet;

public record LoginResponse(
        String accessToken,
        AuthResponse authResponse
) {

    public static LoginResponse of(String token, Member member, List<Pet> pets) {
        return new LoginResponse(
                token,
                AuthResponse.of(member, pets)
        );
    }

}
