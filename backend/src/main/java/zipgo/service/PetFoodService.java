package zipgo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipgo.domain.Keyword;
import zipgo.domain.PetFood;
import zipgo.domain.repository.KeywordRepository;
import zipgo.domain.repository.PetFoodRepository;
import zipgo.exception.KeywordException;

import java.util.List;

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
