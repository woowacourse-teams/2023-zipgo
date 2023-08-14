package zipgo.pet.presentation;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zipgo.auth.presentation.Auth;
import zipgo.auth.presentation.dto.AuthDto;
import zipgo.pet.application.PetService;
import zipgo.pet.presentation.dto.request.CreatePetRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(name = "/pets")
public class PetController {

    private final PetService petService;

    @GetMapping(consumes = {MULTIPART_FORM_DATA_VALUE, APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> create(
            @Auth AuthDto authDto,
            @RequestBody @Valid CreatePetRequest request
    ) {
        Long petId = petService.createPet(authDto.id(), request);
        return ResponseEntity.created(URI.create("/pets/" + petId)).build();
    }

}
