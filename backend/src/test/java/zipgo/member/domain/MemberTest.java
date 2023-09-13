package zipgo.member.domain;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import zipgo.pet.domain.Pet;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Nested
    class 반려동물_여부를_확인 {

        @Test
        void 반려동물이_있을때_true() {
            //given
            Pet 반려동물 = Pet.builder().build();
            Member 주인 = Member.builder().pets(List.of(반려동물)).build();

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
            assertThat(주인.getPets()).contains(반려동물);
        }

        @Test
        void 이미_있는경우_추가하지_않기() {
            //given
            Member 주인 = Member.builder().build();
            Pet 반려동물 = Pet.builder().build();

            주인.addPet(반려동물);

            //when
            주인.addPet(반려동물);

            //then
            assertThat(주인.getPets()).containsOnly(반려동물);
        }

    }

    @Test
    void 반려동물_아이디로_주인여부를_알수있다() {
        //given
        Member 주인 = Member.builder().build();
        Pet 반려동물 = Pet.builder().id(1L).owner(주인).build();
        주인.addPet(반려동물);

        //when
        boolean actual = 주인.isOwnerOf(반려동물);

        //then
        assertThat(actual).isTrue();
    }

}
