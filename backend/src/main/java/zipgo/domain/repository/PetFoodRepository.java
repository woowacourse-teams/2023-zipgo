package zipgo.domain.repository;

import zipgo.domain.Keyword;
import zipgo.domain.PetFood;

import java.util.List;

public interface PetFoodRepository {
    List<PetFood> findAll();

    List<PetFood> findByKeyword(Keyword keyword);

    void save(PetFood petFood);
}
