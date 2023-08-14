package zipgo.pet.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.exception.PetException;

public interface BreedsRepository extends JpaRepository<Breeds, Long> {

    Optional<Breeds> findByName(String name);

    default Breeds getByName(String name) {
        return findByName(name).orElseThrow(PetException.BreedsNotFound::new);
    }

}
