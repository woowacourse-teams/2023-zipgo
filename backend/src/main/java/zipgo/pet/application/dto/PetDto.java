package zipgo.pet.application.dto;

import java.time.Year;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Gender;
import zipgo.pet.domain.Pet;

public record PetDto(
        String name,
        String gender,
        String imageUrl,
        int age,
        String breed,
        String petSize,
        double weight
) {

    public Pet toEntity(Member owner, Breeds breeds) {
        int birthYear = Year.now().getValue() - age;
        return Pet.builder()
                .birthYear(Year.of(birthYear))
                .owner(owner)
                .imageUrl(imageUrl)
                .name(name)
                .gender(Gender.from(gender))
                .breeds(breeds)
                .weight(weight)
                .build();
    }

    public Year calculateBirthYear() {
        return Year.of(Year.now().getValue() - age);
    }

}
