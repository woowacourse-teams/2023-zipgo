package zipgo.petfood.application;


import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.presentation.dto.PrimaryIngredientCreateRequest;
import zipgo.petfood.presentation.dto.PrimaryIngredientSelectResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class PrimaryIngredientService {

    private final PrimaryIngredientRepository primaryIngredientRepository;

    public Long createPrimaryIngredient(PrimaryIngredientCreateRequest request) {
        PrimaryIngredient primaryIngredient = request.toEntity();
        return primaryIngredientRepository.save(primaryIngredient).getId();
    }

    public List<PrimaryIngredientSelectResponse> getPrimaryIngredients() {
        List<PrimaryIngredient> primaryIngredients = primaryIngredientRepository.findDistinctPrimaryIngredients();
        return primaryIngredients.stream()
                .map(primaryIngredient -> PrimaryIngredientSelectResponse.of(primaryIngredient.getId(), primaryIngredient.getName()))
                .collect(Collectors.toList());
    }

}
