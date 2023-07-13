package zipgo.domain.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import zipgo.domain.Keyword;
import zipgo.domain.repository.fake.KeywordFakeRepository;

class KeywordRepositoryTest {
    private KeywordRepository keywordRepository = new KeywordFakeRepository();

    @Test
    void 이름으로_키워드를_가져올_수_있다() {
        // given
        // when
        Optional<Keyword> 조회된_키워드 = keywordRepository.findByName("다이어트");

        // then
        assertThat(조회된_키워드).isNotEmpty();
        assertThat(조회된_키워드.get().getName()).isEqualTo("다이어트");
    }
}