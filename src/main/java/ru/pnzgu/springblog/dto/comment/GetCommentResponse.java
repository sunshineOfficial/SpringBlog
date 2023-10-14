package ru.pnzgu.springblog.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий ответ на получение комментария.
 */
@Data
@AllArgsConstructor
public class GetCommentResponse {
    /**
     * Идентификатор комментария.
     */
    private int id;

    /**
     * Идентификатор пользователя, создавшего комментарий.
     */
    private int userId;

    /**
     * Идентификатор поста.
     */
    private int postId;

    /**
     * Содержимое комментария.
     */
    private String content;

    /**
     * Флаг, указывающий, опубликован ли комментарий.
     */
    private boolean published;

    /**
     * Дата и время создания комментария.
     */
    private Date createdAt;

    /**
     * Дата и время последнего обновления комментария.
     */
    private Date updatedAt;

    /**
     * Дата и время публикации комментария.
     */
    private Date publishedAt;
}
