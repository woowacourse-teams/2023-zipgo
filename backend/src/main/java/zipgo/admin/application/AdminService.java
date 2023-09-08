package zipgo.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandCreateRequest;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final BrandRepository brandRepository;

    public Long createBrand(BrandCreateRequest request, String imageUrl) {
        Brand brand = request.toEntity(imageUrl);
        return brandRepository.save(brand).getId();
    }

}
