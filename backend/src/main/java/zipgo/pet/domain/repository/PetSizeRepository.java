package zipgo.pet.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.PetSize;
import zipgo.pet.exception.PetException;

public interface PetSizeRepository extends JpaRepository<PetSize, Long> {

    Optional<PetSize> findByName(String name);

    default PetSize getByName(String name) {
        return findByName(name).orElseThrow(PetException.PetSizeNotFound::new);
    }

}
