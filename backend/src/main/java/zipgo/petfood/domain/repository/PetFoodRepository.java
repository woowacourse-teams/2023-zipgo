package zipgo.petfood.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.exception.PetFoodException;

public interface PetFoodRepository extends JpaRepository<PetFood, Long> {

    List<PetFood> findByKeyword(Keyword keyword);

    default PetFood getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new PetFoodException.NotFound(id));
    }

}
