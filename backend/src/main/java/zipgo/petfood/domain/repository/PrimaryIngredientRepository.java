package zipgo.petfood.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zipgo.petfood.domain.PrimaryIngredient;

public interface PrimaryIngredientRepository extends JpaRepository<PrimaryIngredient, Long> {

    @Query("select distinct(pi.name) from PrimaryIngredient pi")
    List<String> findDistinctPrimaryIngredients();

}
