package zipgo.pet.application;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import zipgo.common.service.QueryServiceTest;
import zipgo.member.domain.Member;
import zipgo.member.domain.repository.MemberRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.domain.PetSize;
import zipgo.pet.domain.fixture.PetFixture;
import zipgo.pet.domain.repository.BreedsRepository;
import zipgo.pet.domain.repository.PetRepository;
import zipgo.pet.domain.repository.PetSizeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.pet.domain.fixture.PetFixture.반려동물;
import static zipgo.review.fixture.MemberFixture.멤버_이름;

class PetQueryServiceTest extends QueryServiceTest {

    @Autowired
    private BreedsRepository breedsRepository;

    @Autowired
    private PetSizeRepository petSizeRepository;

    @Autowired
    private PetQueryService petQueryService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 사용자의_반려동물_목록을_조회할_수_있다() {
        // given
        PetSize 소형견 = 견종_크기_등록("소형견");
        Breeds 풍산개 = 견종_등록("풍산개", 소형견);
        Member 갈비 = 멤버_이름("갈비");
        memberRepository.save(갈비);
        Pet 상근이 = PetFixture.반려동물_생성("상근이", 갈비, 풍산개);
        petRepository.save(상근이);

        // when
        List<Pet> pets = petQueryService.readMemberPets(갈비.getId());

        // then
        assertAll(
                () -> assertThat(pets).hasSize(1),
                () -> assertThat(pets.get(0).getName()).isEqualTo("상근이"),
                () -> assertThat(pets.get(0).getOwner()).isEqualTo(갈비),
                () -> assertThat(pets.get(0).getBreeds()).isEqualTo(풍산개),
                () -> assertThat(pets.get(0).getBreeds().getPetSize()).isEqualTo(소형견)
        );
    }

    @Test
    void 견종_정보를_조회할_수_있다() {
        // given
        PetSize 소형견 = 견종_크기_등록("소형견");
        Breeds 풍산개 = 견종_등록("풍산개", 소형견);
        Member 갈비 = 멤버_이름("갈비");
        memberRepository.save(갈비);
        Pet 상근이 = PetFixture.반려동물_생성("상근이", 갈비, 풍산개);
        petRepository.save(상근이);

        // when
        Pet 찾은_상근이 = petQueryService.readPet(상근이.getId());

        // then
        assertAll(
                () -> assertThat(찾은_상근이.getName()).isEqualTo("상근이"),
                () -> assertThat(찾은_상근이.getBreeds().getName()).isEqualTo("풍산개"),
                () -> assertThat(찾은_상근이.getBreeds().getPetSize().getName()).isEqualTo("소형견"),
                () -> assertThat(찾은_상근이.getOwner().getName()).isEqualTo("갈비")
        );
    }


    @Nested
    class 견종_조회시 {

        @Test
        void 첫번째_순서는_항상_믹스견이다() {
            // given
            PetSize 소형견 = 견종_크기_등록("소형견");
            견종_등록("포메라니안", 소형견);
            견종_등록("시츄", 소형견);
            견종_등록("푸들", 소형견);

            // when
            List<Breeds> 견종_목록 = petQueryService.readBreeds();

            // then
            assertThat(견종_목록.get(0).getName()).isEqualTo("믹스견");
        }

        @Test
        void 믹스견을_포함하여_조회한다() {
            // given
            PetSize 소형견 = 견종_크기_등록("소형견");
            PetSize 중형견 = 견종_크기_등록("중형견");
            PetSize 대형견 = 견종_크기_등록("대형견");
            견종_등록("푸들", 소형견);
            견종_등록("시츄", 중형견);
            견종_등록("말라뮤트", 대형견);

            // when
            List<Breeds> 견종_목록 = petQueryService.readBreeds();

            // then
            assertAll(
                    () -> assertThat(견종_목록.size()).isNotEqualTo(3),
                    () -> assertThat(견종_목록).hasSize(4),
                    () -> assertThat(견종_목록.get(0).getName()).isEqualTo("믹스견")
            );
        }

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

}