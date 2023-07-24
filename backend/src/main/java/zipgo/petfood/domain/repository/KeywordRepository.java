package zipgo.petfood.domain.repository;

import java.util.List;
import java.util.Optional;
import zipgo.petfood.domain.Keyword;

public interface KeywordRepository {

    Keyword save(Keyword keyword);
    List<Keyword> findAll();
    Optional<Keyword> findByName(String name);

}
