package zipgo.common.util;

import io.jsonwebtoken.lang.Strings;

import java.util.Arrays;
import java.util.List;

import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.EMPTY_LIST;

public class StringUtils {

    public static List<String> convertStringsToCollection(String values) {
        if (Strings.hasText(values)) {
            String decodedValues = decode(values, UTF_8);
            return Arrays.asList(decodedValues.split(","));
        }
        return EMPTY_LIST;
    }

}
