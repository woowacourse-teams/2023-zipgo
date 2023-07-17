package zipgo.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByName(String name);

}
