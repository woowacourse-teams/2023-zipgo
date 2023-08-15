package zipgo.member.domain;

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

}
