package ru.pnzgu.springblog.dto.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс, представляющий запрос на вход в систему. 
 */
@Data
public class LoginRequest {
    /**
     * Логин пользователя. 
     * Должен содержать от 3 до 30 символов. 
     */
    @Size(min = 3, max = 30)
    private String username;

    /**
     * Пароль пользователя. 
     * Должен соответствовать следующим требованиям: 
     * - Содержать хотя бы одну строчную букву [a-z] 
     * - Содержать хотя бы одну заглавную букву [A-Z] 
     * - Содержать хотя бы одну цифру [0-9] 
     * - Состоять из 8 или более символов 
     */
    @Pattern(regexp = "(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\w*[0-9])\\w{8,}")
    private String password;
}
