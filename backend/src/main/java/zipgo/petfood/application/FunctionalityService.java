package zipgo.petfood.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.petfood.presentation.dto.FunctionalityCreateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class FunctionalityService {

    private final FunctionalityRepository functionalityRepository;

    public Long createFunctionality(FunctionalityCreateRequest request) {
        Functionality functionality = request.toEntity();
        return functionalityRepository.save(functionality).getId();
    }

}
