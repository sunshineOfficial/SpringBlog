package ru.pnzgu.springblog.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое в случае ошибки валидации. 
 */
public class ValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3;

    public ValidationException(String message) {
        super(message);
    }
}
