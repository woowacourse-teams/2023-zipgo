package zipgo.petfood.domain.repository;

import java.util.List;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;

public interface PetFoodRepository {

    PetFood save(PetFood petFood);

    List<PetFood> findAll();

    List<PetFood> findByKeyword(Keyword keyword);

}
