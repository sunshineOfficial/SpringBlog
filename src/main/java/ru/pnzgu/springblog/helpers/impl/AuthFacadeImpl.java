package ru.pnzgu.springblog.helpers.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.pnzgu.springblog.helpers.AuthFacade;

@Component
public class AuthFacadeImpl implements AuthFacade {
    @Override
    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public void setAuth(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
