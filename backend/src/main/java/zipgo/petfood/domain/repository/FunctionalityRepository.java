package zipgo.petfood.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zipgo.petfood.domain.Functionality;

public interface FunctionalityRepository extends JpaRepository<Functionality, Long> {

    @Query("select distinct(f.name) from Functionality f")
    List<String> findDistinctFunctionalities();

}
