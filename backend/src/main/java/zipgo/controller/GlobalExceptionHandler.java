package zipgo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zipgo.controller.dto.Response;
import zipgo.exception.KeywordException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({KeywordException.NotFound.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Response<String> handleNotFoundException(Exception exception) {
        return new Response<>(exception.getMessage());
    }
}
