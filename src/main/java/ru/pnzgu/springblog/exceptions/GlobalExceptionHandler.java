package ru.pnzgu.springblog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorModel> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorModel> handleValidationException(ValidationException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorModel> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN);
    }
}
