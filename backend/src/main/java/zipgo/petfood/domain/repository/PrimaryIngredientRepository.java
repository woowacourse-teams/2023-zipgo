package zipgo.petfood.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.exception.PrimaryIngredientNotFoundException;

public interface PrimaryIngredientRepository extends JpaRepository<PrimaryIngredient, Long> {

    @Query("select pi from PrimaryIngredient pi where pi.id in "
            + "(select min(subpi.id) from PrimaryIngredient subpi group by subpi.name)")
    List<PrimaryIngredient> findDistinctPrimaryIngredients();

    default PrimaryIngredient getById(Long id) {
        return findById(id).orElseThrow(() -> new PrimaryIngredientNotFoundException(id));
    }

}
