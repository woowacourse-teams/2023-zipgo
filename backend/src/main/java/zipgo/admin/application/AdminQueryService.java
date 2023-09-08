package zipgo.admin.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandSelectResponse;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.admin.dto.FunctionalitySelectResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryService {

    private final BrandRepository brandRepository;
    private final FunctionalityRepository functionalityRepository;

    public List<BrandSelectResponse> getBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> BrandSelectResponse.of(brand.getId(), brand.getName()))
                .toList();
    }

    public List<FunctionalitySelectResponse> getFunctionalities() {
        List<Functionality> distinctFunctionalities = functionalityRepository.findDistinctFunctionalities();
        return distinctFunctionalities.stream()
                .map(functionality -> FunctionalitySelectResponse.of(functionality.getId(), functionality.getName()))
                .collect(Collectors.toList());
    }

}
