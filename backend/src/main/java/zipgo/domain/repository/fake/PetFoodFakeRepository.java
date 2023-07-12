package zipgo.domain.repository.fake;

import zipgo.domain.Keyword;
import zipgo.domain.PetFood;
import zipgo.domain.repository.PetFoodRepository;

import java.util.ArrayList;
import java.util.List;

public class PetFoodFakeRepository implements PetFoodRepository {
    List<PetFood> petFoods = new ArrayList<>();
    @Override
    public List<PetFood> findAll() {
        return petFoods;
    }

    @Override
    public List<PetFood> findByKeyword(Keyword keyword) {
        return null;
    }

    @Override
    public void save(PetFood petFood) {
        petFoods.add(petFood);
    }
}
