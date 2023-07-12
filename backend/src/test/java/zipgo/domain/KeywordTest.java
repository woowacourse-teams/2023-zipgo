package zipgo.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

class KeywordTest {
    @Test
    void 이름으로_Keyword_를_생성할_수_있다() {
        // given
        final String name = "다이어트";

        // when
        final Keyword keyword = new Keyword(name);

        //given
        assertThat(keyword.getName()).isEqualTo("다이어트");

    }
}