package ru.pnzgu.springblog.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс, представляющий запрос на обновление поста. 
 */
@Data
public class UpdatePostRequest {
    /**
     * Заголовок поста.
     * Должен содержать от 3 до 100 символов.
     */
    @Size(min = 3, max = 100)
    private String title;

    /**
     * Содержимое поста.
     * Не может быть пустым.
     */
    @NotBlank
    private String content;
}
