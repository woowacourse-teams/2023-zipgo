package zipgo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class KeywordTest {
    @Test
    void 이름으로_Keyword_를_생성할_수_있다() {
        // given
        final String name = "다이어트";

        // when
        final Keyword keyword = new Keyword(name);

        // then
        assertThat(keyword.getName()).isEqualTo("다이어트");
    }

    @Test
    void 이름이_같으면_동등하다() {
        //given
        final Keyword 키워드_1 = new Keyword("다이어트");
        final Keyword 키워드_2 = new Keyword("다이어트");

        //when
        boolean 동등함 = 키워드_2.equals(키워드_1);

        //then
        assertThat(동등함).isTrue();
    }
}