package zipgo.petfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.PrimaryIngredient;

public interface PrimaryIngredientRepository extends JpaRepository<PrimaryIngredient, Long> {

}
