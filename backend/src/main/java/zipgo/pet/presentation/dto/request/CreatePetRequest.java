package zipgo.pet.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CreatePetRequest (
        @NotNull String name,
        MultipartFile image,
        @NotNull String gender,
        @NotNull Integer age,
        @NotNull String breeds,
        @NotNull String petSize,
        @NotNull Double weight
) {

}
