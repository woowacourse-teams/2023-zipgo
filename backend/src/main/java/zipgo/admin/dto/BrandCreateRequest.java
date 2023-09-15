package zipgo.admin.dto;

import zipgo.brand.domain.Brand;

public record BrandCreateRequest(
        String name,
        String nation,
        Integer foundedYear,
        boolean hasResearchCenter,
        boolean hasResidentVet
) {

    public Brand toEntity(String imageUrl) {
        return Brand.builder()
                .name(name)
                .nation(nation)
                .imageUrl(imageUrl)
                .foundedYear(foundedYear)
                .hasResearchCenter(hasResearchCenter)
                .hasResidentVet(hasResidentVet)
                .build();
    }

}
