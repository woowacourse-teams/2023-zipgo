package zipgo.pet.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.PetSize;
import zipgo.pet.exception.BreedsNotFoundException;

public interface BreedsRepository extends JpaRepository<Breeds, Long> {

    Optional<Breeds> findByPetSizeAndName(PetSize petSize, String name);

    default Breeds getByPetSizeAndName(PetSize petSize, String name) {
        return findByPetSizeAndName(petSize, name)
                .orElseThrow(BreedsNotFoundException::new);
    }

    Optional<Breeds> findByName(String name);

    default Breeds getByName(String name) {
        return findByName(name).orElseThrow(BreedsNotFoundException::new);
    }

    List<Breeds> findByNameNotContaining(String name);

}
