package zipgo.common.logging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtils {

    public static void info(String message, Object... data) {
        log.info(message, data);
    }

    public static void error(Exception exception) {
        String message = getExceptionMessage(exception.getMessage());

        log.error(message + "\n \t {}", exception);
    }

    private static String getExceptionMessage(String message) {
        if (message == null || message.isBlank()) {
            return "";
        }
        return message;
    }

}
