package zipgo.admin.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.admin.dto.BrandSelectResponse;
import zipgo.brand.domain.repository.BrandRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminQueryService {

    private final BrandRepository brandRepository;

    public List<BrandSelectResponse> getBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> BrandSelectResponse.of(brand.getId(), brand.getName()))
                .toList();
    }
}
