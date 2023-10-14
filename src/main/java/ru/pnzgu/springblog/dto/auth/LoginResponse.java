package ru.pnzgu.springblog.dto.auth;

import lombok.Data;

/**
 * Класс, представляющий ответ на вход в систему.
 */
@Data
public class LoginResponse {
    /**
     * Токен доступа, который будет использоваться для аутентификации пользователя.
     */
    private String accessToken;

    /**
     * Тип токена, по умолчанию установлен как "Bearer".
     */
    private String tokenType = "Bearer ";
    
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
