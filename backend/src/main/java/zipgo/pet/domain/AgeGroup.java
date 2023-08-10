package zipgo.pet.domain;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import zipgo.pet.exception.PetException;

@AllArgsConstructor
public enum AgeGroup {

    PUPPY(0, 1),
    ADULT(1, 7),
    SENIOR(7, 50),
    ;

    private final int start;
    private final int end;

    public static AgeGroup from(int age) {
        return Arrays.stream(values())
                .filter(it -> it.start <= age && age < it.end)
                .findFirst()
                .orElseThrow(PetException.AgeBound::new);
    }

}
