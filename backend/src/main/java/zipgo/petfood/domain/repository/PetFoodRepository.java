package zipgo.petfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.exception.PetFoodNotFoundException;

public interface PetFoodRepository extends JpaRepository<PetFood, Long> {

    default PetFood getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new PetFoodNotFoundException(id));
    }

}
