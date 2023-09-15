package zipgo.pet.domain;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static zipgo.pet.domain.fixture.BreedFixture.믹스견;
import static zipgo.pet.domain.fixture.BreedFixture.시바견;
import static zipgo.pet.domain.fixture.BreedFixture.진돗개;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BreedsTest {


    @ParameterizedTest
    @MethodSource("duplicateMixBreeds")
    void 믹스견은_하나만_존재한다(List<Breed> 믹스견_목록) {
        // given
        Breeds 견종_일급_컬렉션 = Breeds.from(믹스견_목록);

        // when
        List<Breed> 반환된_견종_목록 = 견종_일급_컬렉션.getOrderedBreeds();

        // then
        assertAll(
                () -> assertThat(반환된_견종_목록).hasSize(1),
                () -> assertThat(반환된_견종_목록.get(0).getName()).isEqualTo("믹스견")
        );
    }

    static Stream<Arguments> duplicateMixBreeds() {
        return Stream.of(
                Arguments.arguments(List.of(믹스견)),
                Arguments.arguments(List.of(믹스견, 믹스견)),
                Arguments.arguments(List.of(믹스견, 믹스견, 믹스견)),
                Arguments.arguments(List.of(믹스견, 믹스견, 믹스견, 믹스견)),
                Arguments.arguments(List.of(믹스견, 믹스견, 믹스견, 믹스견, 믹스견)),
                Arguments.arguments(List.of(믹스견, 믹스견, 믹스견, 믹스견, 믹스견, 믹스견))
        );
    }

    @ParameterizedTest
    @MethodSource("breedList")
    void 믹스견은_항상_첫번째_순서다(List<Breed> 견종_목록) {
        // given
        Breeds 견종_일급_컬렉션 = Breeds.from(견종_목록);

        // when
        List<Breed> 반환된_견종_목록 = 견종_일급_컬렉션.getOrderedBreeds();

        // then
        assertThat(반환된_견종_목록.get(0).getName()).isEqualTo("믹스견");
    }

    static Stream<Arguments> breedList() {
        return Stream.of(
                Arguments.arguments(List.of(믹스견, 시바견, 진돗개)),
                Arguments.arguments(List.of(진돗개, 믹스견, 시바견)),
                Arguments.arguments(List.of(시바견, 진돗개, 믹스견))
        );
    }

}
