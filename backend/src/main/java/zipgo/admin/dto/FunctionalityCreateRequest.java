package zipgo.admin.dto;

import zipgo.petfood.domain.Functionality;

public record FunctionalityCreateRequest(
        String name
) {

    public Functionality toEntity() {
        return Functionality.builder()
                .name(name)
                .build();
    }

}
