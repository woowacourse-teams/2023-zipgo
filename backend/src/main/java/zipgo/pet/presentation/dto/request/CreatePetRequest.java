package zipgo.pet.presentation.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import zipgo.pet.application.dto.PetDto;

public record CreatePetRequest(
        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String name,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String gender,

        @NotNull
        String imageUrl,

        @Max(value = 20, message = "나이는 최대 20까지 입력 가능합니다.")
        @Min(value = 0, message = "나이는 최소 0부터 입력 가능합니다.")
        Integer age,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String breed,

        @Nullable
        String petSize,

        @Max(value = 100, message = "몸무게는 최대 100까지 입력 가능합니다.")
        @Min(value = 0, message = "몸무게는 최소 0부터 입력 가능합니다.")
        Double weight
) {

    public PetDto toDto() {
        return new PetDto(name, gender, imageUrl, age, breed, petSize, weight);
    }

}
