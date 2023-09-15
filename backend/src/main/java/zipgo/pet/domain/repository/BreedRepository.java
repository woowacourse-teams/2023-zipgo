package zipgo.pet.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.Breed;
import zipgo.pet.domain.PetSize;
import zipgo.pet.exception.BreedsNotFoundException;

public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findByPetSizeAndName(PetSize petSize, String name);

    default Breed getByPetSizeAndName(PetSize petSize, String name) {
        return findByPetSizeAndName(petSize, name)
                .orElseThrow(BreedsNotFoundException::new);
    }

    Optional<Breed> findByName(String name);

    default Breed getByName(String name) {
        return findByName(name).orElseThrow(BreedsNotFoundException::new);
    }

    List<Breed> findByNameNotContaining(String name);

}
