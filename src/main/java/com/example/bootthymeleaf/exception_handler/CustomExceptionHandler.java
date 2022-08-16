package com.example.bootthymeleaf.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<String> handleBusinessException(BindException bindException) {
        System.out.println(bindException.getAllErrors());
        return new ResponseEntity<>("error", HttpStatus.valueOf(400));
    }

    /*
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> handleSizeLimitExceededException(Exception e) {
        log.error("handleSizeLimitExceededException", e);
        final Response response = Response.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<Response> handleBusinessException(GlobalException e) {
        log.error("handleEntityNotFoundException", e);
        ErrorCode errorCode = e.getErrorCode();
        Response response = Response.of(errorCode, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }*/
}