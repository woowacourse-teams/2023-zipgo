package zipgo.pet.domain;

import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Breeds {

    public static final long MIXED_BREED_ID = 0L;
    private static final int FIRST_PLACE = 0;
    private static final String MIXED_BREED_NAME = "믹스견";
    private static final Breed MIXED_BREED = Breed.builder().id(MIXED_BREED_ID).name(MIXED_BREED_NAME).build();

    private final List<Breed> values;

    public static Breeds from(List<Breed> breeds) {
        List<Breed> uniqueBreeds = breeds.stream()
                .filter(breed -> !breed.getName().equals(MIXED_BREED_NAME))
                .collect(toList());
        uniqueBreeds.add(FIRST_PLACE, MIXED_BREED);
        return new Breeds(uniqueBreeds);
    }

    public List<Breed> getOrderedBreeds() {
        return Collections.unmodifiableList(values);
    }

}
