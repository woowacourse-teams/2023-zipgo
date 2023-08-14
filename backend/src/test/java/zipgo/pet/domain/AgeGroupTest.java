package zipgo.pet.domain;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
                .isInstanceOf(PetException.AgeNotFound.class)
                .hasMessageContaining("분류에 속하지 않는 나이입니다.");
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -5L, 100L})
    void 해당하는_아이디가_없으면_예외가_발생한다(Long id) {
        // expect
        assertThatThrownBy(() -> AgeGroup.from(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("AgeGroup에 해당하는 id가 없습니다.");
    }

    @ParameterizedTest
    @MethodSource("연령대별_최소_출생연도")
    void 연령대에_속하는_최소_나이의_출생연도도_계산할수있다(AgeGroup ageGroup, int 예상_연도) {
        //when
        int 실제_연도 = ageGroup.calculateMinBirthYear().getValue();

        //then
        assertThat(실제_연도).isEqualTo(예상_연도);
    }

    public static Stream<Arguments> 연령대별_최소_출생연도() {
        int 테스트_작성_연도와의_차 = LocalDateTime.now().getYear() - 2023;
        return Stream.of(
                Arguments.of(AgeGroup.ADULT, 2022 + 테스트_작성_연도와의_차),
                Arguments.of(AgeGroup.SENIOR, 2016 + 테스트_작성_연도와의_차),
                Arguments.of(AgeGroup.PUPPY, 2023 + 테스트_작성_연도와의_차)
        );
    }

    @ParameterizedTest
    @MethodSource("연령대별_최대_출생연도")
    void 연령대에_속하는_최대_나이의_출생연도를_계산할수있다(AgeGroup ageGroup, int 예상_연도) {
        //when
        int 실제_연도 = ageGroup.calculateMaxBirthYear().getValue();
        //then
        assertThat(실제_연도).isEqualTo(예상_연도);
    }

    public static Stream<Arguments> 연령대별_최대_출생연도() {
        int 테스트_작성_연도와의_차 = LocalDateTime.now().getYear() - 2023;
        return Stream.of(
                Arguments.of(AgeGroup.ADULT, 2017 + 테스트_작성_연도와의_차),
                Arguments.of(AgeGroup.SENIOR, 1974 + 테스트_작성_연도와의_차),
                Arguments.of(AgeGroup.PUPPY, 2023 + 테스트_작성_연도와의_차)
        );
    }


}
