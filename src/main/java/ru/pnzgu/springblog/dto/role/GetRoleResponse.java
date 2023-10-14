package ru.pnzgu.springblog.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий ответ на получение роли.
 */
@Data
@AllArgsConstructor
public class GetRoleResponse {
    /**
     * Идентификатор роли.
     */
    private int id;

    /**
     * Название роли.
     */
    private String name;

    /**
     * Дата и время создания роли.
     */
    private Date createdAt;

    /**
     * Дата и время последнего обновления роли.
     */
    private Date updatedAt;
}
