package zipgo.petfood.infra.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;

public interface JpaPetFoodRepository extends PetFoodRepository, JpaRepository<PetFood, Long> {

}
