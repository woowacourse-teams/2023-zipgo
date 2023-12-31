package zipgo.auth.dto;

import java.util.List;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Pet;
import zipgo.pet.dto.response.PetResponse;

public record AuthResponse(
        Long id,
        String name,
        String email,
        String profileImageUrl,
        boolean hasPet,
        List<PetResponse> pets
) {


    public static AuthResponse of(Member member, List<Pet> pets) {
        return new AuthResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getProfileImgUrl(),
                member.hasPet(),
                pets.stream()
                        .map(PetResponse::from)
                        .toList()
        );
    }

}
