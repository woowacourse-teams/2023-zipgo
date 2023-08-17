package zipgo.pet.domain.fixture;

import zipgo.pet.domain.PetSize;

public class PetSizeFixture {

    public static PetSize 대형견() {
        return PetSize
                .builder()
                .name("대형견")
                .build();
    }

    public static PetSize 소형견() {
        return PetSize.builder().name("소형견").build();
    }

}
