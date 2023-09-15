package zipgo.pet.domain;

import java.time.Year;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class PetTest {

    @ParameterizedTest(name = "현재 나이를 계산할 수 있다 출생연도 = {0}, 예상나이 = {1}")
    @MethodSource("출생년도_예상나이")
    void 현재_나이를_계산할_수_있다(Year 출생연도, int 예상나이) {
        //given
        Pet pet = Pet.builder()
                .birthYear(출생연도)
                .build();

        //when
        int 계산한_나이 = pet.calculateCurrentAge();

        //then
        assertThat(계산한_나이).isEqualTo(예상나이);
    }

    private static Stream<Arguments> 출생년도_예상나이() {
        int 테스트_작성_연도와의_차 = Year.now().getValue() - 2023;
        return Stream.of(
                Arguments.of(Year.of(2023), 0 + 테스트_작성_연도와의_차),
                Arguments.of(Year.of(1999), 24 + 테스트_작성_연도와의_차),
                Arguments.of(Year.of(2017), 6 + 테스트_작성_연도와의_차),
                Arguments.of(Year.of(2019), 4 + 테스트_작성_연도와의_차)
        );
    }

}
