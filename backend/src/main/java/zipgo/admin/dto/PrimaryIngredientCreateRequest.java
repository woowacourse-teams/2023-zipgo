package zipgo.admin.dto;

import zipgo.petfood.domain.PrimaryIngredient;

public record PrimaryIngredientCreateRequest(
        String name
) {

    public PrimaryIngredient toEntity() {
        return PrimaryIngredient.builder()
                .name(name)
                .build();
    }

}
