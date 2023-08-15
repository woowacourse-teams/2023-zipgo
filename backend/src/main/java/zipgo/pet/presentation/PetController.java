package zipgo.pet.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.presentation.Auth;
import zipgo.auth.presentation.dto.AuthDto;
import zipgo.pet.application.PetService;
import zipgo.pet.presentation.dto.request.CreatePetRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Auth AuthDto authDto,
            @RequestBody @Valid CreatePetRequest request
    ) {
        Long petId = petService.createPet(authDto.id(), request);
        return ResponseEntity.created(URI.create("/pets/" + petId)).build();
    }

}