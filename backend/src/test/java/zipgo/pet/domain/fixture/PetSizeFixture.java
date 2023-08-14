package zipgo.pet.domain.fixture;

import zipgo.pet.domain.PetSize;

public class PetSizeFixture {

    public static PetSize 소형견() {
        return PetSize.builder().name("소형견").build();
    }

}
