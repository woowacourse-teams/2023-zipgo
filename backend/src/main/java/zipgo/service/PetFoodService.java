package zipgo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import zipgo.domain.Keyword;
import zipgo.domain.PetFood;
import zipgo.domain.repository.KeywordRepository;
import zipgo.domain.repository.PetFoodRepository;
import zipgo.exception.KeywordException;

@Service
public class PetFoodService {
    private final PetFoodRepository petFoodRepository;
    private final KeywordRepository keywordRepository;

    public PetFoodService(final PetFoodRepository petFoodRepository, final KeywordRepository keywordRepository) {
        this.petFoodRepository = petFoodRepository;
        this.keywordRepository = keywordRepository;
    }

    public List<PetFood> getAllPetFoods() {
        return petFoodRepository.findAll();
    }

    public List<PetFood> getPetFoodHaving(final String keywordName) {
        Keyword keyword = keywordRepository.findByName(keywordName)
                .orElseThrow(() -> new KeywordException.NotFound(keywordName));

        return petFoodRepository.findByKeyword(keyword);
    }
}
