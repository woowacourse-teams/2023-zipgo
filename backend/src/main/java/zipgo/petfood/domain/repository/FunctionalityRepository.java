package zipgo.petfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.Functionality;

public interface FunctionalityRepository extends JpaRepository<Functionality, Long> {

}
