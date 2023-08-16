package zipgo.pet.presentation.dto.response;

import java.time.Year;
import zipgo.pet.domain.Pet;

public record PetResponse(
        Long petId,
        String name,
        int age,
        String breed,
        String petSize,
        String gender,
        double weight,
        String imageUrl

) {

    public static PetResponse from(Pet pet) {
        int age = Year.now().getValue() - pet.getBirthYear().getValue();
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                age,
                pet.getBreeds().getName(),
                pet.getBreeds().getPetSize().getName(),
                pet.getGender().name(),
                pet.getWeight(),
                pet.getImageUrl()
        );
    }

}
