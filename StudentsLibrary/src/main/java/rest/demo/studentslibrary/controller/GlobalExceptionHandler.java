package rest.demo.studentslibrary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rest.demo.studentslibrary.dto.ErrorResponse;
import rest.demo.studentslibrary.exception.*;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    static final org.slf4j.Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandlerBookNotFound(Exception e, HttpServletRequest request) {
        logger.error("Not Found Exception: " + e.getMessage() + " for " + request.getRequestURI());
        return ErrorResponse.builder().errorCode(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).request(request.getRequestURI()).requestType(request.getMethod()).build();
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandlerStudentNotFound(Exception e) {
        return ErrorResponse.builder().errorCode(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build();
    }

    @ExceptionHandler(BorrowHistoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandlerBorrowHistoryNotFound(Exception e) {
        return ErrorResponse.builder().errorCode(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build();
    }

    @ExceptionHandler(StudentBorrowLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandlerStudentBorrowLimit(Exception e) {
        return ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build();
    }

    @ExceptionHandler(BookIsNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandlerBookIsNotAvailable(Exception e, HttpServletRequest request) {
        logger.error("Bad Request Exception: " + e.getMessage() + " for " + request.getRequestURI());
        return ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).request(request.getRequestURI()).build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandlerBadRequest(Exception e) {
        return ErrorResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build();
    }
}