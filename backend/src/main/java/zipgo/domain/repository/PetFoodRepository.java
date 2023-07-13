package zipgo.domain.repository;

import java.util.List;
import zipgo.domain.Keyword;
import zipgo.domain.PetFood;

public interface PetFoodRepository {
    List<PetFood> findAll();

    List<PetFood> findByKeyword(Keyword keyword);

    void save(PetFood petFood);
}
