package com.test.tracker.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionsHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ErrorResponse handle(BadRequestException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), exception.getMessage());
        log.error("======= [handle][BadRequestException] errorResponse = {}", errorResponse);
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ErrorResponse handle(EntityNotFoundException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), exception.getMessage());
        log.error("======= [handle][EntityNotFoundException] errorResponse = {}", errorResponse);
        return errorResponse;
    }
}
