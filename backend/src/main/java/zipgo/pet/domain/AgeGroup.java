package zipgo.pet.domain;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import zipgo.pet.exception.PetException;

@AllArgsConstructor
public enum AgeGroup {

    PUPPY(0, 1),
    ADULT(1, 7),
    SENIOR(7, 50);

    private final int greaterThan;
    private final int lessThanOrEqual;

    public static AgeGroup from(int age) {
        return Arrays.stream(values())
                .filter(ageGroup -> ageGroup.greaterThan <= age && age < ageGroup.lessThanOrEqual)
                .findFirst()
                .orElseThrow(PetException.NotFound::new);
    }

}
