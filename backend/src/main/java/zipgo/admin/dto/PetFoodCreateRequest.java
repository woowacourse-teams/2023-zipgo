package zipgo.admin.dto;

import java.util.List;
import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.HasStandard;
import zipgo.petfood.domain.PetFood;

public record PetFoodCreateRequest(
        String name,
        String purchaseLink,
        Long brandId,
        List<Long> functionalityIds,
        List<Long> primaryIngredientIds,
        boolean europeStandard,
        boolean usStandard
) {

    public PetFood toEntity(Brand brand, String imageUrl) {
        return PetFood.builder()
                .name(name)
                .purchaseLink(purchaseLink)
                .imageUrl(imageUrl)
                .brand(brand)
                .hasStandard(new HasStandard(usStandard, europeStandard))
                .build();
    }

}
