package zipgo.brand.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.domain.Brand;
import zipgo.brand.domain.repository.BrandRepository;
import zipgo.brand.dto.BrandCreateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Long createBrand(BrandCreateRequest request, String imageUrl) {
        Brand brand = request.toEntity(imageUrl);
        return brandRepository.save(brand).getId();
    }

}
