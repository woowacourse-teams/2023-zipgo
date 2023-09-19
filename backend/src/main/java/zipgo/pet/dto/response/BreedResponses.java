package zipgo.pet.dto.response;

import java.util.List;
import zipgo.pet.domain.Breed;


public record BreedResponses(List<BreedResponse> breeds) {

    public static BreedResponses from(List<Breed> breeds) {
        return new BreedResponses(
                breeds.stream()
                        .map(breed -> new BreedResponse(breed.getId(), breed.getName()))
                        .toList()
        );
    }

    public record BreedResponse(long id, String name) {

    }

}
