package zipgo.petfood.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.KeywordRepository;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.exception.KeywordException;

@Service
@RequiredArgsConstructor
public class PetFoodService {

    private final PetFoodRepository petFoodRepository;
    private final KeywordRepository keywordRepository;

    public List<PetFood> getAllPetFoods() {
        return petFoodRepository.findAll();
    }

    public List<PetFood> getPetFoodHaving(final String keywordName) {
        Keyword keyword = keywordRepository.findByName(keywordName)
                .orElseThrow(() -> new KeywordException.NotFound(keywordName));

        return petFoodRepository.findByKeyword(keyword);
    }

}
