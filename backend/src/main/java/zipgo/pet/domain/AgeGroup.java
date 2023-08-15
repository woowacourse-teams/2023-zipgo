package zipgo.pet.domain;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import zipgo.pet.exception.PetException;

@Getter
@AllArgsConstructor
public enum AgeGroup {

    PUPPY(1L, "퍼피", 0, 1),
    ADULT(2L, "어덜트", 1, 7),
    SENIOR(3L, "시니어", 7, 50);

    private final Long id;
    private final String name;
    private final int greaterThanOrEqual;
    private final int lessThan;

    public static AgeGroup from(int age) {
        return Arrays.stream(values())
                .filter(ageGroup -> ageGroup.greaterThanOrEqual <= age && age < ageGroup.lessThan)
                .findFirst()
                .orElseThrow(PetException.AgeNotFound::new);
    }

}
