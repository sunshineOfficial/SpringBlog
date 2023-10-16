package ru.pnzgu.springblog.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * Изображение поста.
     */
    private MultipartFile image;
}
