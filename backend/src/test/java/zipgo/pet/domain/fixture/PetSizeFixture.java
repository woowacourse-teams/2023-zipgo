package zipgo.pet.domain.fixture;

import zipgo.pet.domain.PetSize;

public class PetSizeFixture {

    public static PetSize 대형견_생성() {
        return PetSize
                .builder()
                .name("대형견")
                .build();
    }

}
