package zipgo.pet.application;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.ServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.pet.presentation.dto.request.CreatePetRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class PetServiceTest extends ServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    @Test
    void 반려견을_등록_할_수_있다() {
        // given
        PetSize 소형견 = 견종_크기_등록("소형견");
        품종_등록("포메라니안", 소형견);
        Member 가비 = 멤버_등록("가비");

        // when
        Long petId = petService.createPet(가비.getId(), 반려견_등록_요청("갈비"));

        // then
        Pet 반려견 = petRepository.findById(petId).get();
        assertAll(
                () -> assertThat(반려견.getOwner().getName()).isEqualTo("가비"),
                () -> assertThat(반려견.getName()).isEqualTo("갈비"),
                () -> assertThat(반려견.getBreeds().getName()).isEqualTo("포메라니안")
        );
    }

    @Test
    void 등록된_품종_정보를_모두_가져올_수_있다() {
        // given
        견종_크기_등록("소형견");
        견종_크기_등록("중형견");
        견종_크기_등록("대형견");

        // when
        List<PetSize> petSizes = petSizeRepository.findAll();

        // then
        assertAll(
                () -> assertThat(petSizes).hasSize(3),
                () -> assertThat(petSizes.get(0).getName()).isEqualTo("소형견"),
                () -> assertThat(petSizes.get(1).getName()).isEqualTo("중형견"),
                () -> assertThat(petSizes.get(2).getName()).isEqualTo("대형견")
        );
    }


    private PetSize 견종_크기_등록(String 견종_크기) {
        PetSize petSize = PetSize
                .builder()
                .name(견종_크기)
                .build();
        return petSizeRepository.save(petSize);
    }

    private Breeds 품종_등록(String 품종_이름, PetSize 크기) {
        Breeds 품종 = Breeds.builder()
                .name(품종_이름)
                .petSize(크기)
                .build();
        return breedsRepository.save(품종);
    }

    private CreatePetRequest 반려견_등록_요청(String 반려견_이름) {
        return new CreatePetRequest(반려견_이름, "남", 반려견_이름 + "img", 5, "포메라니안", "소형견", 65.4);
    }

    private Member 멤버_등록(String 이름) {
        Member member = Member.builder().profileImgUrl("사진사진").email(이름 + "@zipgo.com").name(이름).build();
        return memberRepository.save(member);
    }

}
