package zipgo.pet.domain;

import java.time.Year;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static zipgo.pet.domain.fixture.PetFixture.대형견_20년생_남아;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PetTest {

    @Test
    void 나이를_계산할_수_있다() {
        // given
        Year 기준_년도 = Year.of(2023);
        Year 현재_년도 = mock(Year.class);
        when(현재_년도.getValue()).thenReturn(기준_년도.getValue());

        Pet _20년생_반려견 = 대형견_20년생_남아();
        int 예상_나이 = 기준_년도.getValue() - _20년생_반려견.getBirthYear().getValue();

        // when
        int 실제_나이 = _20년생_반려견.calculateAge();

        // 기대하는 나이와 실제 계산된 나이 비교
        assertThat(예상_나이).isEqualTo(실제_나이);
    }

}
