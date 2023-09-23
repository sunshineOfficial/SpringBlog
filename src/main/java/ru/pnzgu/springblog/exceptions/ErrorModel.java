package ru.pnzgu.springblog.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorModel {
    private int statusCode;
    private String message;
    private Date timestamp;
}
