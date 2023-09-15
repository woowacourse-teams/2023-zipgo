package zipgo.petfood.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.exception.FunctionalityNotFoundException;

public interface FunctionalityRepository extends JpaRepository<Functionality, Long> {

    @Query("select f from Functionality f where f.id in "
            + "(select min(subf.id) from Functionality subf group by subf.name)")
    List<Functionality> findDistinctFunctionalities();

    default Functionality getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new FunctionalityNotFoundException(id));
    }

    Optional<Functionality> findByName(String name);

    default Functionality getByName(String name) {
        return findByName(name)
                .orElseThrow(() -> new FunctionalityNotFoundException(name));
    }

}
