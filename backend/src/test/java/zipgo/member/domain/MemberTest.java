package zipgo.member.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import zipgo.member.exception.PetAlreadyRegisteredException;
import zipgo.pet.domain.Pet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MemberTest {

    @Nested
    class 반려동물_여부를_확인 {

        @Test
        void 반려동물이_있을때_true() {
            //given
            Pet 반려동물 = Pet.builder().build();
            Member 주인 = Member.builder().pet(반려동물).build();

            // expect
            assertThat(주인.hasPet()).isTrue();
        }

        @Test
        void 반려동물이_없을때_true() {
            //given
            Member 주인 = Member.builder().build();

            // expect
            assertThat(주인.hasPet()).isFalse();
        }

    }

    @Nested
    class 반려동물_추가하기 {

        @Test
        void 성공적으로_추가() {
            //given
            Member 주인 = Member.builder().build();
            Pet 반려동물 = Pet.builder().build();

            //when
            주인.addPet(반려동물);

            //then
            assertThat(주인.getPet()).isEqualTo(반려동물);
        }

        @Test
        void 이미_반려동물이_있으면_예외가_발생() {
            //given
            Member 주인 = Member.builder().pet(Pet.builder().build()).build();
            Pet 반려동물 = Pet.builder().build();

            //expect
            assertThatThrownBy(() -> 주인.addPet(반려동물))
                    .isInstanceOf(PetAlreadyRegisteredException.class)
                    .hasMessage("이미 반려동물이 있습니다.");
        }

    }

}
