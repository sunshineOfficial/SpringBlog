package ru.pnzgu.springblog.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое в случае отказа в доступе. 
 */
public class AccessDeniedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4;

    public AccessDeniedException(String message) {
        super(message);
    }
}
