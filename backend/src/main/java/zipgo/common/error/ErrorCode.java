package zipgo.common.error;

import org.springframework.http.HttpStatus;

public record ErrorCode(HttpStatus status, String message) {

}
