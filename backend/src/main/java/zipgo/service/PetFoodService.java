package zipgo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import zipgo.domain.PetFood;
import zipgo.domain.repository.PetFoodRepository;

@Service
public class PetFoodService {
    private final PetFoodRepository petFoodRepository;
    public PetFoodService(final PetFoodRepository petFoodRepository) {
        this.petFoodRepository = petFoodRepository;
    }

    public List<PetFood> getAllPetFoods() {
        return petFoodRepository.findAll();
    }
}
