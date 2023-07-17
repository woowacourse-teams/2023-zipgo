package zipgo.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import zipgo.domain.Keyword;
import zipgo.domain.PetFood;

public interface PetFoodRepository extends JpaRepository<PetFood, Long> {
    List<PetFood> findAll();

    List<PetFood> findByKeyword(Keyword keyword);
}
