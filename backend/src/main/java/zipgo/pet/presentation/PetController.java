package zipgo.pet.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.dto.AuthCredentials;
import zipgo.auth.presentation.Auth;
import zipgo.pet.application.PetQueryService;
import zipgo.pet.application.PetService;
import zipgo.pet.domain.Breed;
import zipgo.pet.domain.Pet;
import zipgo.pet.dto.request.CreatePetRequest;
import zipgo.pet.dto.request.UpdatePetRequest;
import zipgo.pet.dto.response.BreedsResponses;
import zipgo.pet.dto.response.PetResponse;
import zipgo.pet.dto.response.PetResponses;

@RestController
@AllArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final PetQueryService petQueryService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Auth AuthCredentials authCredentials,
            @RequestBody @Valid CreatePetRequest createPetRequest
    ) {
        Long petId = petService.createPet(authCredentials.id(), createPetRequest.toDto());
        return ResponseEntity.created(URI.create("/pets/" + petId)).build();
    }

    @PutMapping("/{petId}")
    public ResponseEntity<Void> update(
            @Auth AuthCredentials authCredentials,
            @PathVariable("petId") Long petId,
            @RequestBody @Valid UpdatePetRequest updatePetRequest
    ) {
        petService.updatePet(authCredentials.id(), petId, updatePetRequest.toDto());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> delete(
            @Auth AuthCredentials authCredentials,
            @PathVariable("petId") Long petId
    ) {
        petService.deletePet(authCredentials.id(), petId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PetResponses> readMemberPets(@Auth AuthCredentials authCredentials) {
        List<Pet> pets = petQueryService.readMemberPets(authCredentials.id());
        return ResponseEntity.ok(PetResponses.from(pets));
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> readPet(
            @PathVariable("petId") Long petId
    ) {
        Pet pet = petQueryService.readPet(petId);
        return ResponseEntity.ok(PetResponse.from(pet));
    }

    @GetMapping("/breeds")
    public ResponseEntity<BreedsResponses> readBreeds() {
        List<Breed> breeds = petQueryService.readBreeds();
        return ResponseEntity.ok(BreedsResponses.from(breeds));
    }

}

