package ru.pnzgu.springblog.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Компонент, представляющий точку входа для аутентификации с использованием JWT.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * Вызывается при возникновении ошибки аутентификации и отправляет ответ с кодом HTTP 401 (Unauthorized) и сообщением об ошибке.
     * 
     * @param request           объект HttpServletRequest, представляющий текущий HTTP-запрос
     * @param response          объект HttpServletResponse, представляющий текущий HTTP-ответ
     * @param authException     исключение AuthenticationException, которое было выброшено при аутентификации
     * @throws IOException      если возникает ошибка ввода-вывода при отправке ответа
     * @throws ServletException если возникает ошибка сервлета при обработке запроса
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
