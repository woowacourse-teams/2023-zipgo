package zipgo.pet.application;

import java.time.Year;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.ServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.member.exception.MemberNotFoundException;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;
import zipgo.pet.exception.OwnerNotMatchException;
import zipgo.pet.exception.PetNotFoundException;
import zipgo.pet.dto.request.CreatePetRequest;
import zipgo.pet.dto.request.UpdatePetRequest;
import zipgo.review.fixture.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.pet.domain.Gender.MALE;


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
        Breeds 포메라니안 = 견종_등록("포메라니안", 소형견);
        Member 가비 = 멤버_등록("가비");

        // when
        Long petId = petService.createPet(가비.getId(), 반려견_등록_요청("쫑이", 포메라니안).toDto());

        // then
        Pet 쫑이 = petRepository.findById(petId).get();
        assertAll(
                () -> assertThat(쫑이.getOwner().getName()).isEqualTo("가비"),
                () -> assertThat(쫑이.getName()).isEqualTo("쫑이"),
                () -> assertThat(쫑이.getBreeds().getName()).isEqualTo("포메라니안")
        );
    }

    @Test
    void 믹스견을_등록_할_수_있다() {
        // given
        PetSize 소형견 = 견종_크기_등록("소형견");
        PetSize 중형견 = 견종_크기_등록("중형견");
        PetSize 대형견 = 견종_크기_등록("중형견");
        Breeds 소형_믹스견 = 견종_등록("믹스견", 소형견);
        견종_등록("믹스견", 중형견);
        견종_등록("믹스견", 대형견);
        Member 가비 = 멤버_등록("가비");

        // when
        Long petId = petService.createPet(가비.getId(), 믹스견_등록_요청("쫑이", 소형_믹스견).toDto());

        // then
        Pet 쫑이 = petRepository.findById(petId).get();
        assertAll(
                () -> assertThat(쫑이.getOwner().getName()).isEqualTo("가비"),
                () -> assertThat(쫑이.getName()).isEqualTo("쫑이"),
                () -> assertThat(쫑이.getBreeds().getName()).isEqualTo("믹스견")
        );
    }

    @Test
    void 반려견_정보를_수정할_수_있다() {
        // given
        Pet 생성된_쫑이 = 쫑이_등록하기();
        Long 쫑이_주인_id = 생성된_쫑이.getOwner().getId();

        // when
        petService.updatePet(쫑이_주인_id, 생성된_쫑이.getId(), 반려견_몸무게_수정_요청("쫑이", 80.0).toDto());

        // then
        Pet 쫑이 = petRepository.findById(생성된_쫑이.getId()).get();
        assertAll(
                () -> assertThat(쫑이.getName()).isEqualTo("쫑이"),
                () -> assertThat(쫑이.getWeight()).isEqualTo(80.0)
        );
    }

    @Test
    void 수정시_반려견과_주인이_일치하지_않으면_예외가_발생한다() {
        // given
        Pet 쫑이 = 쫑이_등록하기();
        Long 다른사람_id = 99999L;

        // expect
        assertThatThrownBy(() -> petService.updatePet(다른사람_id, 쫑이.getId(), 반려견_몸무게_수정_요청("쫑이", 80.0).toDto()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원을 찾을 수 없습니다. 알맞은 회원인지 확인해주세요.");
    }

    @Test
    void 반려견을_삭제할_수_있다() {
        // given
        Pet 생성된_쫑이 = 쫑이_등록하기();
        Long 쫑이_주인_id = 생성된_쫑이.getOwner().getId();

        // when
        petService.deletePet(쫑이_주인_id, 생성된_쫑이.getId());

        // then
        assertThatThrownBy(() -> petRepository.getById(생성된_쫑이.getId()))
                .isInstanceOf(PetNotFoundException.class)
                .hasMessageContaining("존재하지 않는 반려동물입니다. 다시 확인해주세요.");
    }

    @Test
    void 반려견과_주인이_일치하지_않으면_예외가_발생한다() {
        // given
        Pet 생성된_쫑이 = 쫑이_등록하기();
        Member 무민 = memberRepository.save(MemberFixture.무민());

        // expect
        assertThatThrownBy(() -> petService.deletePet(무민.getId(), 생성된_쫑이.getId()))
                .isInstanceOf(OwnerNotMatchException.class)
                .hasMessageContaining("반려견과 주인이 일치하지 않습니다. 다시 확인해주세요.");
    }

    private PetSize 견종_크기_등록(String 견종_크기) {
        PetSize petSize = PetSize
                .builder()
                .name(견종_크기)
                .build();
        return petSizeRepository.save(petSize);
    }

    private Breeds 견종_등록(String 견종_이름, PetSize 크기) {
        Breeds 견종 = Breeds.builder()
                .name(견종_이름)
                .petSize(크기)
                .build();
        return breedsRepository.save(견종);
    }

    private CreatePetRequest 반려견_등록_요청(String 반려견_이름, Breeds 품종) {
        return new CreatePetRequest(반려견_이름, "남", 반려견_이름 + "img", 5, 품종.getName(), null, 65.4);
    }

    private CreatePetRequest 믹스견_등록_요청(String 반려견_이름, Breeds 품종) {
        return new CreatePetRequest(반려견_이름, "남", 반려견_이름 + "img", 5, 품종.getName(), "소형견", 65.4);
    }

    private UpdatePetRequest 반려견_몸무게_수정_요청(String 반려견_이름, double 수정할_몸무게) {
        return new UpdatePetRequest(반려견_이름, "남", 반려견_이름 + "img", 5, "포메라니안", "소형견", 수정할_몸무게);
    }

    private Member 멤버_등록(String 이름) {
        Member member = Member.builder().profileImgUrl("사진사진").email(이름 + "@zipgo.com").name(이름).build();
        return memberRepository.save(member);
    }

    private Pet 쫑이_등록하기() {
        PetSize 소형견 = 견종_크기_등록("소형견");
        Breeds 포메라니안 = 견종_등록("포메라니안", 소형견);
        Member 가비 = 멤버_등록("가비");
        Pet 쫑이 = Pet.builder()
                .name("쫑이")
                .owner(가비)
                .gender(MALE)
                .breeds(포메라니안)
                .birthYear(Year.of(2005))
                .imageUrl("쫑이_사진")
                .weight(35.5)
                .build();
        return petRepository.save(쫑이);
    }

}
