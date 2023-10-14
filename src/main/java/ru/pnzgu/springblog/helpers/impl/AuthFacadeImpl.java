package ru.pnzgu.springblog.helpers.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.pnzgu.springblog.helpers.AuthFacade;

/**
 * Класс для получения и установки аутентификации.
 */
@Component
public class AuthFacadeImpl implements AuthFacade {

    /**
     * Возвращает текущую аутентификацию.
     *
     * @return объект Authentication, представляющий текущую аутентификацию
     */
    @Override
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Устанавливает новую аутентификацию.
     *
     * @param auth объект Authentication, представляющий новую аутентификацию
     */
    @Override
    public void setAuth(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
