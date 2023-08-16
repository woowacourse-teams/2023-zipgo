package zipgo.pet.presentation.dto.response;

import java.util.List;
import zipgo.pet.domain.Pet;

public record PetResponses (
        List<PetResponse> petResponses
){

    public static PetResponses from(List<Pet> pets) {
        return new PetResponses(
                pets.stream()
                        .map(PetResponse::from)
                        .toList()
        );
    }

}
