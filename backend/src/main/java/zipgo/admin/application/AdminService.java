package zipgo.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.petfood.domain.Functionality;
import zipgo.petfood.domain.repository.FunctionalityRepository;
import zipgo.admin.dto.FunctionalityCreateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final BrandRepository brandRepository;
    private final FunctionalityRepository functionalityRepository;

    public Long createBrand(BrandCreateRequest request, String imageUrl) {
        Brand brand = request.toEntity(imageUrl);
        return brandRepository.save(brand).getId();
    }

    public Long createFunctionality(FunctionalityCreateRequest request) {
        Functionality functionality = request.toEntity();
        return functionalityRepository.save(functionality).getId();
    }

}
