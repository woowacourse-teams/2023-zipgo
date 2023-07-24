package zipgo.petfood.stub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;

public class FakePetFoodRepository implements PetFoodRepository {

    private final Map<Long, PetFood> petFoods = new HashMap<>();

    public Long id = 1L;

    @Override
    public PetFood save(PetFood petFood) {
        petFoods.put(id, petFood);
        PetFood response = generatePetFood(id, petFood);
        this.id++;
        return response;
    }

    @Override
    public List<PetFood> findAll() {
        return petFoods.keySet().stream()
                .map(id -> generatePetFood(id, petFoods.get(id)))
                .toList();
    }

    @Override
    public List<PetFood> findByKeyword(Keyword keyword) {
        return petFoods.keySet().stream()
                .filter(id -> petFoods.get(id).getKeyword() != null)
                .filter(id -> petFoods.get(id).getKeyword().equals(keyword))
                .map(id -> generatePetFood(id, petFoods.get(id)))
                .toList();
    }

    public PetFood generatePetFood(Long id, PetFood petFood) {
        return new PetFood(id, petFood.getName(), petFood.getPurchaseLink(), petFood.getImageUrl(), petFood.getKeyword());
    }

}
