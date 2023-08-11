package zipgo.pet.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import zipgo.pet.exception.PetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AgeGroupTest {

    @Test
    void 한_살_미만은_PUPPY다() {
        // given
        int age = 0;

        // when
        AgeGroup 나이대 = AgeGroup.from(age);

        // then
        assertThat(나이대).isEqualTo(AgeGroup.PUPPY);
    }

    @ParameterizedTest(name = "한 살 이상 일곱 살 미만은 ADULT다. 입력 = {0}")
    @ValueSource(ints = {1, 6})
    void 한_살_이상_일곱_살_미만은_ADULT다(int age) {
        // when
        AgeGroup 나이대 = AgeGroup.from(age);

        // then
        assertThat(나이대).isEqualTo(AgeGroup.ADULT);
    }

    @Test
    void 일곱_살_이상은_SENIOR다() {
        // given
        int age = 7;

        // when
        AgeGroup 나이대 = AgeGroup.from(age);

        // then
        assertThat(나이대).isEqualTo(AgeGroup.SENIOR);
    }

    @ParameterizedTest(name = "분류에 속하지 않는 나이는 예외가 발생한다. 입력 = {0}")
    @ValueSource(ints = {-1, 51, Integer.MAX_VALUE})
    void 나이_그룹에_속하지_않는_수는_예외가_발생한다(int age) {
        // expect
        assertThatThrownBy(() -> AgeGroup.from(age))
                .isInstanceOf(PetException.NotFound.class)
                .hasMessageContaining("분류에 속하지 않는 나이입니다.");
    }

}
