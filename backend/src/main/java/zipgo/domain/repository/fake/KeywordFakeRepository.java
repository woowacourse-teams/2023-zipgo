package zipgo.domain.repository.fake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import zipgo.domain.Keyword;
import zipgo.domain.repository.KeywordRepository;

@Repository
public class KeywordFakeRepository implements KeywordRepository {

    List<Keyword> keywords = new ArrayList<>(Arrays.asList(
            new Keyword("diet")
    ));

    @Override
    public Optional<Keyword> findByName(final String name) {
        return keywords.stream()
                .filter(keyword -> keyword.getName().equals(name))
                .findFirst();
    }
}
