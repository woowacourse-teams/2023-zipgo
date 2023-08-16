package zipgo.pet.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.Year;
import zipgo.member.domain.Member;
import zipgo.pet.domain.Breeds;
import zipgo.pet.domain.Gender;
import zipgo.pet.domain.Pet;

public record CreatePetRequest (

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String name,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String gender,

        String image,

        @Max(20)
        @Min(0)
        Integer age,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String breed,

        @NotBlank(message = "Null 또는 공백이 포함될 수 없습니다. 올바른 값인지 확인해주세요.")
        String petSize,

        @Min(0)
        Double weight
) {

        public static Pet toEntity(CreatePetRequest request, Member owner, Breeds breeds) {
                int birthYear = Year.now().getValue() - request.age();
                return Pet.builder()
                        .birthYear(Year.of(birthYear))
                        .owner(owner)
                        .name(request.name())
                        .gender(Gender.from(request.gender()))
                        .breeds(breeds)
                        .weight(request.weight())
                        .build();
        }

}
