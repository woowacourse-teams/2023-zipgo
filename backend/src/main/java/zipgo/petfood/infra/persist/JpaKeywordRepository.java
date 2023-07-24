package zipgo.petfood.infra.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.repository.KeywordRepository;

public interface JpaKeywordRepository extends KeywordRepository, JpaRepository<Keyword, Long> {
}
