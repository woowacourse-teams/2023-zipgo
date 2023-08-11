package zipgo.pet.domain.fixture;

import java.time.Year;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.BreedsSize;
import zipgo.pet.domain.Gender;
import zipgo.pet.domain.Pet;
import zipgo.review.fixture.MemberFixture;

public class PetFixture {

    public static final Pet 대형견_20년생_남아() {
        return Pet.builder()
                .weight(20.0)
                .name("상근이")
                .birthYear(Year.of(2023))
                .breeds(허스키_대형견())
                .gender(Gender.MALE)
                .member(MemberFixture.무민())
                .build();

    }

    public static final Breeds 허스키_대형견() {
        return Breeds.builder()
                .breedsSize(BreedsSize.LARGE)
                .name("시베리안 허스키")
                .build();
    }

}
