package zipgo.pet.dto.response;

import java.util.List;
import zipgo.pet.domain.Breeds;


public record BreedsResponses(List<BreedsResponse> breeds) {

    public static BreedsResponses from(List<Breeds> breeds) {
        return new BreedsResponses(
                breeds.stream()
                        .map(breed -> new BreedsResponse(breed.getId(), breed.getName()))
                        .toList()
        );
    }

    public record BreedsResponse(long id, String name) {

    }

}
