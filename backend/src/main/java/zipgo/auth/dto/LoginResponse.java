package zipgo.auth.dto;

import java.util.List;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Pet;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        AuthResponse authResponse
) {

    public static LoginResponse of(TokenDto tokenDto, Member member, List<Pet> pets) {
        return new LoginResponse(
                tokenDto.accessToken(),
                tokenDto.refreshToken(),
                AuthResponse.of(member, pets)
        );
    }

}
