package ru.pnzgu.springblog.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий ответ на получение пользователя.
 */
@Data
@AllArgsConstructor
public class GetUserResponse {
    /**
     * Идентификатор пользователя.
     */
    private int id;

    /**
     * Идентификатор роли.
     */
    private int roleId;

    /**
     * Логин пользователя.
     */
    private String username;

    /**
     * Имя пользователя.
     */
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    private String lastName;

    /**
     * Дата и время создания пользователя.
     */
    private Date createdAt;

    /**
     * Дата и время последнего обновления роли.
     */
    private Date updatedAt;
}
