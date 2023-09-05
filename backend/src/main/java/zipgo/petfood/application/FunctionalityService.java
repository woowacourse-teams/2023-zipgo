package zipgo.petfood.application;


import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.presentation.dto.FunctionalityCreateRequest;
import zipgo.petfood.presentation.dto.FunctionalitySelectResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class FunctionalityService {

    private final FunctionalityRepository functionalityRepository;

    public Long createFunctionality(FunctionalityCreateRequest request) {
        Functionality functionality = request.toEntity();
        return functionalityRepository.save(functionality).getId();
    }

    public List<FunctionalitySelectResponse> getFunctionalities() {
        List<Functionality> distinctFunctionalities = functionalityRepository.findDistinctFunctionalities();
        return distinctFunctionalities.stream()
                .map(functionality -> FunctionalitySelectResponse.of(functionality.getId(), functionality.getName()))
                .collect(Collectors.toList());
    }

}
