package zipgo.common.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    BREEDS("breeds"),
    PET_FOODS("petFoods")
    ;

    CacheType(String name) {
        this.name = name;
        this.maxSize = 10000;
        this.expireTime = 3000;
    }

    private final String name;
    private final int maxSize;
    private final long expireTime;

}
