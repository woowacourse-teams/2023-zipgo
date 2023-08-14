package zipgo.pet.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import zipgo.pet.exception.PetException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GenderTest {

    @Test
    void 존재하지_않는_성별을_찾을_경우_예외가_발생한다() {
        // given
        String 존재하지_않는_성별 = "중성화";

        // expect
        assertThatThrownBy(() -> Gender.from(존재하지_않는_성별))
                .isInstanceOf(PetException.GenderNotFound.class)
                .hasMessageContaining("존재하지 않는 성볇입니다.");
    }

}
