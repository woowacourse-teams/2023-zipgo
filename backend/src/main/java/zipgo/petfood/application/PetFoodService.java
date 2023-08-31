package zipgo.petfood.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.brand.application.BrandQueryService;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.PetFood;
import zipgo.petfood.domain.repository.PetFoodRepository;
import zipgo.petfood.presentation.dto.PetFoodCreateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class PetFoodService {

    private final PetFoodRepository petFoodRepository;
    private final BrandQueryService brandQueryService;

    public Long createPetFood(PetFoodCreateRequest request, String imageUrl) {
        Brand brand = brandQueryService.findBrand(request.brandId());
        PetFood petFood = request.toEntity(brand, imageUrl);
        return petFoodRepository.save(petFood).getId();
    }

}
