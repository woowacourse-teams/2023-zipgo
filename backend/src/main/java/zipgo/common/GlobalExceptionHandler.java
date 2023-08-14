package zipgo.common;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zipgo.auth.exception.AuthException;
import zipgo.common.logging.LoggingUtils;
import zipgo.member.exception.MemberException;
import zipgo.petfood.exception.PetFoodException;
import zipgo.petfood.presentation.dto.ErrorResponse;
import zipgo.review.exception.ReviewException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            PetFoodException.NotFound.class,
            ReviewException.NotFound.class,
            MemberException.NotFound.class,
            AuthException.ResourceNotFound.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception exception) {
        LoggingUtils.error(exception);
        return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.of(exception));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        LoggingUtils.error(exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse("서버 내부 오류"));
    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(Exception exception) {
        LoggingUtils.error(exception);
        return ResponseEntity.status(UNAUTHORIZED).body(ErrorResponse.of(exception));
    }

    @ExceptionHandler({AuthException.Forbidden.class})
    public ResponseEntity<ErrorResponse> handleForbiddenException(Exception exception) {
        LoggingUtils.error(exception);
        return ResponseEntity.status(FORBIDDEN).body(ErrorResponse.of(exception));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        if (isAlreadyCommitted(request)) {
            return null;
        }
        LoggingUtils.error(exception);
        return ResponseEntity.status(statusCode).body(ErrorResponse.of(exception));
    }

    private boolean isAlreadyCommitted(WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        HttpServletResponse response = servletWebRequest.getResponse();
        return response != null && response.isCommitted();
    }

}
