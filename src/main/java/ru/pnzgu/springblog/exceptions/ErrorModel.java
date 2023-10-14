package ru.pnzgu.springblog.exceptions;

import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий ответ на запрос, содержащий ошибку.
 */
@Data
public class ErrorModel {
    private int statusCode;
    private String message;
    private Date timestamp;
}
