package zipgo.common.logging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtils {

    public static void info(String message, Object... data) {
        log.info(message, data);
    }

    public static void error(Exception exception) {
        String message = getExceptionMessage(exception.getMessage());
        StackTraceElement[] stackTraceElement = exception.getStackTrace();

        log.error(message + "\n \t {}", stackTraceElement[0]);
    }

    private static String getExceptionMessage(String message) {
        if (message.isBlank()) {
            return "";
        }
        return message;
    }


}
