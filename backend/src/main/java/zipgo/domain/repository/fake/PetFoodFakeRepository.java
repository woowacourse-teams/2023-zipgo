package zipgo.domain.repository.fake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;
import zipgo.domain.Keyword;
import zipgo.domain.PetFood;
import zipgo.domain.repository.PetFoodRepository;

@Repository
public class PetFoodFakeRepository implements PetFoodRepository {
    List<PetFood> petFoods = new ArrayList<>(Arrays.asList(
            new PetFood("[고집] 돌아온 배배", "https://github.com/woowacourse-teams/2023-zipgo",
                    "https://avatars.githubusercontent.com/u/94087228?v=4", new Keyword("diet")),
            new PetFood("[고집] 갈비 맛 모밀", "https://github.com/woowacourse-teams/2023-zipgo",
                    "https://avatars.githubusercontent.com/u/76938931?v=4")
    ));
    @Override
    public List<PetFood> findAll() {
        return petFoods;
    }

    @Override
    public List<PetFood> findByKeyword(Keyword keyword) {
        return petFoods.stream()
                .filter(petFood -> keyword.equals(petFood.getKeyword()))
                .toList();
    }

    @Override
    public void save(PetFood petFood) {
        petFoods.add(petFood);
    }
}
