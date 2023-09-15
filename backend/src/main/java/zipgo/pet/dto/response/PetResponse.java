package zipgo.pet.dto.response;

import java.time.Year;

import zipgo.pet.domain.AgeGroup;
import zipgo.pet.domain.Pet;

public record PetResponse(
        Long id,
        String name,
        int age,
        Long breedId,
        String breed,
        String petSize,
        Long ageGroupId,
        String ageGroup,
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
                pet.getBreeds().getId(),
                pet.getBreeds().getName(),
                pet.getBreeds().getPetSize().getName(),
                AgeGroup.from(age).getId(),
                AgeGroup.from(age).getName(),
                pet.getGender().getValue(),
                pet.getWeight(),
                pet.getImageUrl()
        );
    }

}
