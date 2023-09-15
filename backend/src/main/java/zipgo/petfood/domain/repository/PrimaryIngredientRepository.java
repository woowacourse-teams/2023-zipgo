package zipgo.petfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.exception.PrimaryIngredientNotFoundException;

public interface PrimaryIngredientRepository extends JpaRepository<PrimaryIngredient, Long> {

    default PrimaryIngredient getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new PrimaryIngredientNotFoundException(id));
    }

}
