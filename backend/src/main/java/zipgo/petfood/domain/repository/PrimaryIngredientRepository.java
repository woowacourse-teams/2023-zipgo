package zipgo.petfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.exception.PrimaryIngredientNotFoundException;

public interface PrimaryIngredientRepository extends JpaRepository<PrimaryIngredient, Long> {

    default PrimaryIngredient getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new PrimaryIngredientNotFoundException(id));
    }

    Optional<PrimaryIngredient> findByName(String name);

    default PrimaryIngredient getByName(String name) {
        return findByName(name)
                .orElseThrow(() -> new PrimaryIngredientNotFoundException(name));
    }

}
