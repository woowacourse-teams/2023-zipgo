package zipgo.pet.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.Breeds;
import zipgo.pet.exception.PetException;

public interface BreedsRepository extends JpaRepository<Breeds, Long> {

    Optional<Breeds> findByNameAndPetSizeId(String name, Long petSizeId);

    default Breeds getByNameAndPetSizeId(String name, Long petSizeId) {
        return findByNameAndPetSizeId(name, petSizeId).orElseThrow(PetException.BreedsNotFound::new);
    }

}
