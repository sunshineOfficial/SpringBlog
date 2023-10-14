package ru.pnzgu.springblog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Компонент, представляющий фильтр для обработки аутентификации с использованием JWT.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Выполняет основную логику фильтрации запросов.
     * 
     * @param request           объект HttpServletRequest, представляющий текущий HTTP-запрос
     * @param response          объект HttpServletResponse, представляющий текущий HTTP-ответ
     * @param filterChain       объект FilterChain для передачи запроса дальше по цепочке фильтров
     * @throws ServletException если возникает ошибка сервлета при обработке запроса
     * @throws IOException      если возникает ошибка ввода-вывода при обработке запроса
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getJwtFromRequest(request);

        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
            var username = jwtGenerator.getUsernameFromJwt(token);
            var userDetails = customUserDetailsService.loadUserByUsername(username);
            var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Извлекает JWT из HTTP-запроса.
     * 
     * @param request объект HttpServletRequest, представляющий текущий HTTP-запрос
     * @return строку, содержащую JWT, или null, если токен отсутствует
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        var bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
