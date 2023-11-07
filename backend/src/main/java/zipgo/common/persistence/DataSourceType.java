package zipgo.common.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataSourceType {

    SOURCE(Key.SOURCE_NAME),
    REPLICA(Key.REPLICA_NAME),
    ;

    private final String key;

    public static class Key {

        public static final String ROUTING_NAME = "ROUTING";
        public static final String SOURCE_NAME = "SOURCE";
        public static final String REPLICA_NAME = "REPLICA";

    }

}
