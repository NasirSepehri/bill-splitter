package com.example.billsplitter.exception;


import com.example.billsplitter.component.MessageByLocaleComponent;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "internal.server.server.err.msg";

    private final MessageByLocaleComponent messageByLocaleComponent;

    @Autowired
    public GlobalExceptionHandler(MessageByLocaleComponent messageByLocaleComponent) {
        this.messageByLocaleComponent = messageByLocaleComponent;
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ProblemDetail> httpMessageNotReadableException(Exception exception, WebRequest request) {
        log.info(exception.getMessage(), exception);
        return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST,
                messageByLocaleComponent.getMessage("invalid.value"), request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SQLGrammarException.class, BadSqlGrammarException.class, SQLException.class})
    public ResponseEntity<ProblemDetail> sqlException(SQLGrammarException exception, WebRequest request) {
        log.info(exception.getMessage(), exception);
        String message = messageByLocaleComponent.getMessage(INTERNAL_SERVER_ERROR_MESSAGE);
        return new ResponseEntity<>(createResponse(INTERNAL_SERVER_ERROR, message, request), INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ProblemDetail> methodArgumentNotValidException(Exception exception, WebRequest request) {
        log.info(exception.getMessage(), exception);
        String message = "";
        message = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors().stream().map(fieldError ->
                fieldError.getField() + " in " + fieldError.getObjectName() + " " + fieldError.getDefaultMessage()

        ).collect(Collectors.joining(" \n "));
        return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST, message, request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> noHandlerFoundException(NoHandlerFoundException exception, WebRequest request) {
        return new ResponseEntity<>(createResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({
            AppException.class,
            AppException.AuthorizationFailed.class,
            AppException.BadRequest.class,
            AppException.Forbidden.class,
            AppException.NotFound.class,
            AppException.ServerError.class,
            AppException.ValidationFailure.class,
    })
    public ResponseEntity<ProblemDetail> exceptionHandler(Exception exception, WebRequest request) {
        log.info(exception.getMessage(), exception);
        String message = exception.getMessage();
        Throwable throwable = exception.getCause() != null ? exception.getCause() : exception;
        ResponseStatus httpStatusAnnotation = getExceptionErrorCode(throwable);
        HttpStatus httpStatus = httpStatusAnnotation != null ? httpStatusAnnotation.value() : INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(createResponse(httpStatus, message, request), httpStatus);
    }

    @Order
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ProblemDetail> handleRuntimeExceptions(Exception exception, WebRequest request) {
        log.info(exception.getMessage(), exception);
        createResponse(INTERNAL_SERVER_ERROR, messageByLocaleComponent.getMessage(INTERNAL_SERVER_ERROR_MESSAGE), request);
        return new ResponseEntity<>(createResponse(INTERNAL_SERVER_ERROR,
                messageByLocaleComponent.getMessage(INTERNAL_SERVER_ERROR_MESSAGE), request), INTERNAL_SERVER_ERROR);
    }

    private ResponseStatus getExceptionErrorCode(Throwable exception) {
        return AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
    }

    private ProblemDetail createResponse(HttpStatus httpStatus, String message, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus.value());
        problemDetail.setDetail(message);
        log.info(problemDetail.toString());
        return problemDetail;
    }
}
