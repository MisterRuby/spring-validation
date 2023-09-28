package ruby.requestvalidation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ruby.requestvalidation.ErrorResponse;

@RestControllerAdvice
public class ExceptionController {

    public static final String BIND_EXCEPTION_MESSAGE = "형식에 맞지 않는 값이 존재합니다.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse bindExceptionHandler(BindException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(BIND_EXCEPTION_MESSAGE)
                .build();

        e.getFieldErrors().forEach(errorResponse::addValidation);
        return errorResponse;
    }
}
