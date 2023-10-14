package ru.pnzgu.springblog.dto.comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Класс, представляющий запрос на создание комментария. 
 */
@Data
public class CreateCommentRequest {
    /**
     * Идентификатор поста. 
     * Не может быть менее 1.
     */
    @Min(1)
    private int postId;

    /**
     * Содержимое комментария.
     * Не может быть пустым.
     */
    @NotBlank
    private String content;
}
