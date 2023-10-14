package ru.pnzgu.springblog.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое в случае не нахождения сущности. 
 */
public class EntityNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
