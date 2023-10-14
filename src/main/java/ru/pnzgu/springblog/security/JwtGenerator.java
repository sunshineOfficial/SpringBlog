package ru.pnzgu.springblog.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Компонент, предоставляющий методы для генерации, извлечения и проверки JWT.
 */
@Component
public class JwtGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Генерирует JWT на основе аутентификации.
     * 
     * @param authentication объект Authentication, представляющий информацию об аутентификации
     * @return строку, содержащую сгенерированный JWT
     */
    public String generateToken(Authentication authentication) {
        var username = authentication.getName();
        var currentDate = new Date();
        var expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Извлекает логин пользователя из JWT.
     * 
     * @param token строка, содержащая JWT
     * @return логин, извлеченный из JWT
     */
    public String getUsernameFromJwt(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Проверяет, является ли JWT действительным.
     * 
     * @param token строка, содержащая JWT
     * @return true, если токен действительный, иначе false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
