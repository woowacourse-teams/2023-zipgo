package zipgo.pet.domain.fixture;

import zipgo.pet.domain.Breed;
import zipgo.pet.domain.PetSize;

public class BreedFixture {

    public static Breed 견종_생성(String 이름, PetSize 사이즈) {
        return Breed.builder()
                .petSize(사이즈)
                .name(이름)
                .build();
    }

    public static Breed 견종(PetSize 사이즈) {
        return Breed.builder()
                .name("푸들")
                .petSize(사이즈)
                .build();
    }

    public static Breed 견종(PetSize 사이즈, String 이름) {
        return Breed.builder()
                .name(이름)
                .petSize(사이즈)
                .build();
    }

    public static final Breed 믹스견 = Breed.builder().name("믹스견").build();
    public static final Breed 시바견 = Breed.builder().name("시바견").build();
    public static final Breed 진돗개 = Breed.builder().name("진돗개").build();

}
