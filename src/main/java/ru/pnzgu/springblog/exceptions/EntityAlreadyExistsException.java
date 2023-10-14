package ru.pnzgu.springblog.exceptions;

import java.io.Serial;

/**
 * Исключение, выбрасываемое в случае попытки создания уже существующей сущности. 
 */
public class EntityAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 2;

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
