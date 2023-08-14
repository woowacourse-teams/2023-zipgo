package zipgo.pet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
