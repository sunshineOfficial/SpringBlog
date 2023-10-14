package ru.pnzgu.springblog.exceptions;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Класс, представляющий ответ на запрос, содержащий ошибку валидации.
 */
@Data
public class ValidationErrorModel {
    private int statusCode;
    private String message;
    private Map<String, String> errors;
    private Date timestamp;
}
