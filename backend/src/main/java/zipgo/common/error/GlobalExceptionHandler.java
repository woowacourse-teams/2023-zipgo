package zipgo.common.error;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zipgo.common.logging.LoggingUtils;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ZipgoException.class)
    public ResponseEntity<ErrorResponse> handlingApplicationException(ZipgoException exception) {
        LoggingUtils.warn(exception);
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.status())
                .body(new ErrorResponse(errorCode.status().value(), errorCode.message()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handlingServerErrorException(Exception exception) {
        LoggingUtils.error(exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "서버에서 알 수 없는 오류가 발생했습니다."));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        if (isAlreadyCommitted(request)) {
            return null;
        }
        LoggingUtils.error(exception);
        return ResponseEntity.status(statusCode)
                .body(new ErrorResponse(statusCode.value(), exception.getMessage()));
    }

    private boolean isAlreadyCommitted(WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        HttpServletResponse response = servletWebRequest.getResponse();
        return response != null && response.isCommitted();
    }

}
