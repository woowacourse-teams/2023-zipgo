package zipgo.pet.domain.fixture;

import java.time.Year;
import zipgo.member.domain.Member;
import zipgo.member.domain.fixture.MemberFixture;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Gender;
import zipgo.pet.domain.Pet;

public class PetFixture {

    public static Pet 반려동물_생성(){
        return Pet.builder()
                .id(1L)
                .name("첵스네강아지")
                .imageUrl("https://image.zipgo.pet/dev/pet-image/dog_icon.svg")
                .owner(MemberFixture.식별자_있는_멤버())
                .gender(Gender.MALE)
                .birthYear(Year.of(2019))
                .breeds(BreedsFixture.견종(PetSizeFixture.대형견()))
                .weight(5.0)
                .build();
    }

    public static Pet 반려동물_생성(Member 주인, Breeds 종류) {
        return 반려동물_생성("무민이", 주인, 종류);
    }

    public static Pet 반려동물_생성(String 이름, Member 주인, Breeds 종류) {
        return Pet.builder()
                .name(이름)
                .imageUrl("https://image.zipgo.pet/dev/pet-image/dog_icon.svg")
                .owner(주인)
                .gender(Gender.MALE)
                .birthYear(Year.of(2019))
                .breeds(종류)
                .weight(5.0)
                .build();
    }

}
