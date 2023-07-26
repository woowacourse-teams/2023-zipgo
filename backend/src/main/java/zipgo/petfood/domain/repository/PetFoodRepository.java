package zipgo.petfood.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;

public interface PetFoodRepository extends JpaRepository<PetFood, Long> {

    List<PetFood> findByKeyword(Keyword keyword);

}
