package zipgo.petfood.presentation.dto;

import zipgo.brand.domain.Brand;

public record BrandResponse(
        Long id,
        String brandUrl,
        String brandName
) {

    public static BrandResponse from(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getImageUrl(), brand.getName());
    }

}
