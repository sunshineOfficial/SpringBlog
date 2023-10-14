package ru.pnzgu.springblog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;

/**
 * Глобальный обработчик исключений.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Обрабатывает исключение EntityNotFoundException.
     * 
     * @param ex      исключение EntityNotFoundException, которое было выброшено
     * @param request объект WebRequest, представляющий текущий запрос
     * @return модель ошибки ErrorModel и код состояния 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorModel> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключение EntityAlreadyExistsException.
     * 
     * @param ex      исключение EntityAlreadyExistsException, которое было выброшено
     * @param request объект WebRequest, представляющий текущий запрос
     * @return модель ошибки ErrorModel и код состояния 400
     */
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorModel> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение ValidationException.
     * 
     * @param ex      исключение ValidationException, которое было выброшено
     * @param request объект WebRequest, представляющий текущий запрос
     * @return модель ошибки ErrorModel и код состояния 400
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorModel> handleValidationException(ValidationException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение AccessDeniedException.
     * 
     * @param ex      исключение AccessDeniedException, которое было выброшено
     * @param request объект WebRequest, представляющий текущий запрос
     * @return модель ошибки ErrorModel и код состояния 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorModel> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        var errorModel = new ErrorModel();
        errorModel.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorModel.setMessage(ex.getMessage());
        errorModel.setTimestamp(new Date());

        return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN);
    }

    /**
     * Обрабатывает исключение MethodArgumentNotValidException.
     * 
     * @param ex      исключение MethodArgumentNotValidException, которое было выброшено
     * @param request объект WebRequest, представляющий текущий запрос
     * @return модель ошибки ValidationErrorModel и код состояния 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        var errorModel = new ValidationErrorModel();
        errorModel.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorModel.setMessage("Data is not valid");
        errorModel.setTimestamp(new Date());

        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errorModel.setErrors(errors);

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }
}
