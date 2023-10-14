package ru.pnzgu.springblog.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Класс, представляющий объект передачи данных для страницы, содержащей список элементов типа T.
 * @param <T> тип элементов списка
 */
@Data
@AllArgsConstructor
public class PageDto<T> {
    /**
     * Список элементов типа T.
     */
    private List<T> content;

    /**
     * Номер страницы.
     */
    private int pageNumber;

    /**
     * Количество элементов на странице.
     */
    private int pageSize;

    /**
     * Общее количество элементов в списке.
     */
    private long totalElements;

    /**
     * Общее количество страниц.
     */
    private int totalPages;

    /**
     * Флаг, указывающий, является ли текущая страница последней.
     */
    private boolean last;
}
