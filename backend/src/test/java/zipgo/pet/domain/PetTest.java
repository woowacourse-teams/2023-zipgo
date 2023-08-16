package zipgo.pet.domain;

import java.time.Year;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PetTest {


    @ParameterizedTest
    @MethodSource("출생년도_예상나이")
    void 현재_나이_계산하기(Year 출생연도, int 예상나이) {
        //given
        Pet pet = Pet.builder()
                .birthYear(출생연도)
                .build();

        //when
        int 계산한_나이 = pet.calculateCurrentAge();

        //then
        Assertions.assertThat(계산한_나이).isEqualTo(예상나이);
    }

    public static Stream<Arguments> 출생년도_예상나이() {
        return Stream.of(
                Arguments.of(Year.of(2023), 0),
                Arguments.of(Year.of(1999), 24),
                Arguments.of(Year.of(2017), 6),
                Arguments.of(Year.of(2019), 4)
        );
    }


}
