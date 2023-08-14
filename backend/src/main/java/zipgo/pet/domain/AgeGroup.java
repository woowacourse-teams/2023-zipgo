package zipgo.pet.domain;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import zipgo.pet.exception.PetException;

@AllArgsConstructor
public enum AgeGroup {

    PUPPY(0, 1),
    ADULT(1, 7),
    SENIOR(7, 50);

    private final int greaterThanOrEqual;
    private final int lessThan;

    public static AgeGroup from(int age) {
        return Arrays.stream(values())
                .filter(ageGroup -> ageGroup.greaterThanOrEqual <= age && age < ageGroup.lessThan)
                .findFirst()
                .orElseThrow(PetException.AgeNotFound::new);
    }

}
