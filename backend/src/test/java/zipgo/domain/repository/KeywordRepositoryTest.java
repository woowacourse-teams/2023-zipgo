package zipgo.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import zipgo.domain.Keyword;
import zipgo.domain.repository.fake.KeywordFakeRepository;

class KeywordRepositoryTest {
    private KeywordRepository keywordRepository = new KeywordFakeRepository();

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