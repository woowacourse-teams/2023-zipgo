package zipgo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.domain.Keyword;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByName(String name);
}
