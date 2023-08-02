package zipgo.petfood.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import zipgo.petfood.domain.Keyword;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KeywordRepositoryTest {

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    void 이름으로_키워드를_가져올_수_있다() {
        // given
        // when
        Optional<Keyword> 조회된_키워드 = keywordRepository.findByName("diet");

        // then
        assertThat(조회된_키워드).isNotEmpty();
        assertThat(조회된_키워드.get().getName()).isEqualTo("diet");
    }

}
