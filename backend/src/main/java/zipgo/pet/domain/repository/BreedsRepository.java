package zipgo.pet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.pet.domain.Breeds;

public interface BreedsRepository extends JpaRepository<Breeds, Long> {

}
