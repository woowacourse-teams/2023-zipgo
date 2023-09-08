package zipgo.brand.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandQueryService {

    private final BrandRepository brandRepository;

    public Brand findBrand(Long brandId) {
        return brandRepository.getById(brandId);
    }

}

