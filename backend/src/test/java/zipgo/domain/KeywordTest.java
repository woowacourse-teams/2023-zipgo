package zipgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KeywordTest {
    @Test
    void 이름으로_Keyword_를_생성할_수_있다() {
        // given
        String name = "다이어트";

        // when
        Keyword keyword = new Keyword(name);

        // then
        assertThat(keyword.getName()).isEqualTo("다이어트");
    }

    @Test
    void 아이디가_같으면_동등하다() {
        //given
        Keyword 키워드_1 = new Keyword(1L, "다이어트");
        Keyword 키워드_2 = new Keyword(1L, "다이어트");

        //when
        boolean 동등함 = 키워드_2.equals(키워드_1);

        //then
        assertThat(동등함).isTrue();
    }
}
