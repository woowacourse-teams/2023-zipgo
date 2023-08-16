package zipgo.pet.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.presentation.Auth;
import zipgo.auth.presentation.dto.AuthDto;
import zipgo.pet.application.PetQueryService;
import zipgo.pet.application.PetService;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Pet;
import zipgo.pet.presentation.dto.request.CreatePetRequest;
import zipgo.pet.presentation.dto.request.UpdatePetRequest;
import zipgo.pet.presentation.dto.response.BreedsResponses;
import zipgo.pet.presentation.dto.response.PetResponse;
import zipgo.pet.presentation.dto.response.PetResponses;

@RestController
@AllArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final PetQueryService petQueryService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Auth AuthDto authDto,
            @RequestBody @Valid CreatePetRequest createPetRequest
    ) {
        Long petId = petService.createPet(authDto.id(), createPetRequest.toDto());
        return ResponseEntity.created(URI.create("/pets/" + petId)).build();
    }

    @PutMapping("/{petId}")
    public ResponseEntity<Void> update(
            @Auth AuthDto authDto,
            @PathVariable("petId") Long petId,
            @RequestBody @Valid UpdatePetRequest updatePetRequest
    ) {
        petService.updatePet(authDto.id(), petId, updatePetRequest.toDto());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PetResponses> readMemberPets(@Auth AuthDto authDto) {
        List<Pet> pets = petQueryService.readMemberPets(authDto.id());
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
        List<Breeds> breeds = petQueryService.readBreeds();
        return ResponseEntity.ok(BreedsResponses.from(breeds));
    }

}
