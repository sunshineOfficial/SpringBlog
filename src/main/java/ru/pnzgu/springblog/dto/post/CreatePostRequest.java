package ru.pnzgu.springblog.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс, представляющий запрос на создание поста. 
 */
@Data
public class CreatePostRequest {
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
