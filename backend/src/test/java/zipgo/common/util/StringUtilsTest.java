package zipgo.common.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class StringUtilsTest {

    @Test
    void convertStringsToCollection() {
        // given
        final String values = "1,2,3";

        // when
        final var result = StringUtils.convertStringsToCollection(values);

        // then
        assertAll(
                () -> assertThat(result.get(0)).isEqualTo("1"),
                () -> assertThat(result.get(1)).isEqualTo("2"),
                () -> assertThat(result.get(2)).isEqualTo("3")
        );
    }

    @Test
    void convertStringsToCollectionIfEmpty() {
        // given
        final String values = "";

        // when
        final var result = StringUtils.convertStringsToCollection(values);

        // then
        assertThat(result).isEmpty();
    }
}