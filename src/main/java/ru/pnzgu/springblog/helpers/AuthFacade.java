package ru.pnzgu.springblog.helpers;

import org.springframework.security.core.Authentication;

/**
 * Интерфейс для получения и установки аутентификации.
 */
public interface AuthFacade {
    /**
     * Возвращает текущую аутентификацию.
     * 
     * @return объект Authentication, представляющий текущую аутентификацию
     */
    Authentication getAuth();

    /**
     * Устанавливает новую аутентификацию.
     * 
     * @param auth объект Authentication, представляющий новую аутентификацию
     */
    void setAuth(Authentication auth);
}
