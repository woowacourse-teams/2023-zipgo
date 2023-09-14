package zipgo.admin.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandSelectResponse;
import zipgo.admin.dto.FunctionalitySelectResponse;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.PrimaryIngredient;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.domain.repository.PrimaryIngredientRepository;
import zipgo.admin.dto.PrimaryIngredientSelectResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryService {

    private final BrandRepository brandRepository;
    private final FunctionalityRepository functionalityRepository;
    private final PrimaryIngredientRepository primaryIngredientRepository;

    public List<BrandSelectResponse> getBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> BrandSelectResponse.of(brand.getId(), brand.getName()))
                .toList();
    }

    public List<FunctionalitySelectResponse> getFunctionalities() {
        List<Functionality> distinctFunctionalities = functionalityRepository.findAll();
        return distinctFunctionalities.stream()
                .map(functionality -> FunctionalitySelectResponse.of(functionality.getId(), functionality.getName()))
                .collect(Collectors.toList());
    }

    public List<PrimaryIngredientSelectResponse> getPrimaryIngredients() {
        List<PrimaryIngredient> primaryIngredients = primaryIngredientRepository.findAll();
        return primaryIngredients.stream()
                .map(primaryIngredient -> PrimaryIngredientSelectResponse.of(primaryIngredient.getId(), primaryIngredient.getName()))
                .collect(Collectors.toList());
    }

}
