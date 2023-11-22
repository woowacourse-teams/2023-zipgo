package zipgo.common.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {

    BREEDS("breeds", 1, 10),
    ;

    private final String name;
    private final int maxSize;
    private final long expireTime;

}
