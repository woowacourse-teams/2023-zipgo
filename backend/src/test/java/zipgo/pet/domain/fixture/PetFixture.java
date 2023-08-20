package zipgo.pet.domain.fixture;

import java.time.Year;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;

public class PetFixture {

    public static Pet 반려동물(Member 주인, Breeds 종류) {
        return 반려동물_생성("무민이", 주인, 종류);
    }

    public static Pet 반려동물_생성(String 이름, Member 주인, Breeds 종류) {
        Pet 반려동물 = Pet.builder()
                .name(이름)
                .imageUrl("https://image.zipgo.pet/dev/pet-image/dog_icon.svg")
                .owner(주인)
                .birthYear(Year.of(2019))
                .breeds(종류)
                .weight(5.0)
                .build();
        주인.addPet(반려동물);
        return 반려동물;
    }

}
