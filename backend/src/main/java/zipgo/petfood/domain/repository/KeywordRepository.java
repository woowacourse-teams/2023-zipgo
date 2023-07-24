package zipgo.petfood.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByName(String name);

}
