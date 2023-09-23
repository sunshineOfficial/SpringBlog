package ru.pnzgu.springblog.services;

import ru.pnzgu.springblog.dto.auth.LoginRequest;
import ru.pnzgu.springblog.dto.auth.LoginResponse;
import ru.pnzgu.springblog.dto.auth.RegisterRequest;

public interface AuthService {
    int register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
