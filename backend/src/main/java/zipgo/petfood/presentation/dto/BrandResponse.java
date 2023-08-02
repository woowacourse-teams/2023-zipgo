package zipgo.petfood.presentation.dto;

import zipgo.brand.domain.Brand;

public record BrandResponse(
        Long id,
        String brandUrl,
        String brandName
) {

    public static BrandResponse from(Brand brand) {
        // TODO: brand.getName 바꿔야함(2번째 프로퍼티)
        return new BrandResponse(brand.getId(), brand.getName(), brand.getName());
    }

}
