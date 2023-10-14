package ru.pnzgu.springblog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий ответ на получение поста.
 */
@Data
@AllArgsConstructor
public class GetPostResponse {
    /**
     * Идентификатор поста.
     */
    private int id;

    /**
     * Идентификатор пользователя, создавшего пост.
     */
    private int userId;

    /**
     * Заголовок поста.
     */
    private String title;

    /**
     * Содержимое поста.
     */
    private String content;

    /**
     * Флаг, указывающий, опубликован ли пост.
     */
    private boolean published;

    /**
     * Дата и время создания поста.
     */
    private Date createdAt;

    /**
     * Дата и время последнего обновления поста.
     */
    private Date updatedAt;

    /**
     * Дата и время публикации поста.
     */
    private Date publishedAt;
}
