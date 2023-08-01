package zipgo.petfood.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KeywordTest {

    @Test
    void 이름으로_Keyword_를_생성할_수_있다() {
        // given
        String name = "다이어트";

        // when
        Keyword keyword = Keyword.builder()
                .name(name)
                .build();

        // then
        assertThat(keyword.getName()).isEqualTo("다이어트");
    }

    @Test
    void 아이디가_같으면_동등하다() {
        //when
        Keyword 키워드_1 = new Keyword(1L, "다이어트");
        Keyword 키워드_2 = new Keyword(1L, "히히");

        //then
        assertThat(키워드_2).isEqualTo(키워드_1);
    }

    @Test
    void 아이디가_다르면_동등하지않다() {
        //when
        Keyword 키워드_1 = new Keyword(1L, "다이어트");
        Keyword 키워드_2 = new Keyword(2L, "다이어트");

        //then
        assertThat(키워드_2).isNotEqualTo(키워드_1);
    }

}
