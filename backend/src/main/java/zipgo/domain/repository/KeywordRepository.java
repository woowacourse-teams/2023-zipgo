package zipgo.domain.repository;

import java.util.Optional;
import zipgo.domain.Keyword;

public interface KeywordRepository {
    Optional<Keyword> findByName(String name);
}
