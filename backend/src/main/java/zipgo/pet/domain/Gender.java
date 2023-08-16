package zipgo.pet.domain;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import zipgo.pet.exception.PetAgeNotFoundException;
import zipgo.pet.exception.PetGenderNotFoundException;

@RequiredArgsConstructor
public enum Gender {

    MALE("남"),
    FEMALE("여"),
    ;

    private final String value;

    public static Gender from(String other) {
        return Arrays.stream(values())
                .filter(gender -> gender.value.equals(other))
                .findFirst()
                .orElseThrow(PetGenderNotFoundException::new);
    }

}
