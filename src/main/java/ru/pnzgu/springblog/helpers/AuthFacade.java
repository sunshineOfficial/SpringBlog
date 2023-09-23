package ru.pnzgu.springblog.helpers;

import org.springframework.security.core.Authentication;

public interface AuthFacade {
    Authentication getAuth();
    void setAuth(Authentication auth);
}
