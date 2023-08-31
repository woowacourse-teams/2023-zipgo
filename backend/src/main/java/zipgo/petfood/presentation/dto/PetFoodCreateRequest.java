package zipgo.petfood.presentation.dto;

import zipgo.brand.domain.Brand;
import zipgo.petfood.domain.HasStandard;
import zipgo.petfood.domain.PetFood;

public record PetFoodCreateRequest(
        String name,
        String purchaseLink,
        Long brandId,
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
