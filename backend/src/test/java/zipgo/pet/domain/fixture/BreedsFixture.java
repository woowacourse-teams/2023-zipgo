package zipgo.pet.domain.fixture;

import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.PetSize;

public class BreedsFixture {

    public static Breeds 품종_생성(String 이름, PetSize 대형견) {
        return Breeds.builder()
                .petSize(대형견)
                .name(이름)
                .build();
    }

    public static Breeds 견종(PetSize 사이즈) {
        return Breeds.builder()
                .name("푸들")
                .petSize(사이즈)
                .build();
    }

}
