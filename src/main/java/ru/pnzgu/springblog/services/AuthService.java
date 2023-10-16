package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.auth.LoginRequest;
import ru.pnzgu.springblog.dto.auth.LoginResponse;
import ru.pnzgu.springblog.dto.auth.RegisterRequest;

import java.io.IOException;

/**
 * Интерфейс сервиса аутентификации и регистрации пользователей.
 */
public interface AuthService {
    /**
     * Регистрирует нового пользователя.
     * 
     * @param request объект запроса на регистрацию
     * @return идентификатор зарегистрированного пользователя
     */
    int register(RegisterRequest request) throws IOException;

    /**
     * Аутентифицирует пользователя. 
     *
     * @param request объект запроса на аутентификацию 
     * @return информацию об аутентифицированном пользователе 
     */
    LoginResponse login(LoginRequest request);
}
