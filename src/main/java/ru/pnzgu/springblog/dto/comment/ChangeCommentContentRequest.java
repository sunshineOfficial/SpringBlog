package ru.pnzgu.springblog.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Класс, представляющий запрос на смену содержимого комментария. 
 */
@Data
public class ChangeCommentContentRequest {
    /**
     * Содержимое комментария.
     * Не может быть пустым.
     */
    @NotBlank
    private String content;
}
