package zipgo.pet.domain.fixture;

import java.time.Year;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;

public class PetFixture {

    public static Pet 반려동물(Member 주인, Breeds 종류) {
        return Pet.builder()
                .name("무민이")
                .owner(주인)
                .birthYear(Year.of(2019))
                .breeds(종류)
                .weight(5.0)
                .build();
    }

}
