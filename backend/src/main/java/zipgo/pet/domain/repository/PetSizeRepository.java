package zipgo.pet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.PetSize;

public interface PetSizeRepository extends JpaRepository<PetSize, Long> {

}
