package zipgo.pet.domain;

import lombok.Getter;

@Getter
public enum BreedsSize {

    SMALL("소형견"),
    MEDIUM("중형견"),
    LARGE("대형견"),
    ;

    private final String value;

    BreedsSize(String value) {
        this.value = value;
    }

}
