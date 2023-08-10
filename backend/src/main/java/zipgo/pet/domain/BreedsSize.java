package zipgo.pet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BreedsSize {

    SMALL("소형견"),
    MEDIUM("중형견"),
    LARGE("대형견"),
    ;

    private final String value;

}
