package ru.pnzgu.springblog.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.springblog.dto.auth.LoginRequest;
import ru.pnzgu.springblog.dto.auth.LoginResponse;
import ru.pnzgu.springblog.dto.auth.RegisterRequest;
import ru.pnzgu.springblog.services.AuthService;

import java.io.IOException;

/**
 * Контроллер аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Регистрирует нового пользователя. 
     *
     * @param request объект запроса на регистрацию 
     * @return код ответа и идентификатор зарегистрированного пользователя 
     */
    @PostMapping("/register")
    public ResponseEntity<Integer> register(@Valid @ModelAttribute RegisterRequest request) throws IOException {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Аутентифицирует пользователя. 
     *
     * @param request объект запроса на аутентификацию 
     * @return код ответа и информацию об аутентифицированном пользователе 
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
