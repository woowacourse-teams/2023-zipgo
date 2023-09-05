package zipgo.petfood.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.petfood.presentation.dto.FunctionalityCreateRequest;
import zipgo.petfood.presentation.dto.PrimaryIngredientCreateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class PrimaryIngredientService {

    private final PrimaryIngredientRepository primaryIngredientRepository;

    public Long createPrimaryIngredient(PrimaryIngredientCreateRequest request) {
        PrimaryIngredient primaryIngredient = request.toEntity();
        return primaryIngredientRepository.save(primaryIngredient).getId();
    }

}
