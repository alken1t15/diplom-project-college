package kz.alken1t15.backratinglogcollege.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.alken1t15.backratinglogcollege.contoller.ControllerMain;
import kz.alken1t15.backratinglogcollege.dto.LoginAuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    private Logger logger = LoggerFactory.getLogger(ControllerMain.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null) {
            if (headerAuth.startsWith("Bearer ")) {
                String token = headerAuth.substring(7);
                logger.info(String.format("JWT который был получен: %s", token));
                LoginAuth loginAuth = jwtUtil.validateTokenAndRetrieveSubject(token);
                if (loginAuth == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Не правильный JWT токен");
                    response.setCharacterEncoding("UTF-8");
                    return;
                }
                String login = loginAuth.getLogin();
                String password = loginAuth.getPassword();
                LocalDate date = loginAuth.getDate();
                logger.info(String.format("Данные полученные из JWT: логин: %s пароль: %s", login, password));
                Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
                Authentication authenticationUser = authenticationManager.authenticate(authentication);
                if (authenticationUser.isAuthenticated()) {
                    if (date.isAfter(LocalDate.now())) {
                        SecurityContext securityContext = SecurityContextHolder.getContext();
                        securityContext.setAuthentication(authenticationUser);
                        SecurityContextHolder.setContext(securityContext);
                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Истек срок JWT токен");
                        response.setCharacterEncoding("UTF-8");
                        return;
                    }
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Не правильный JWT токен");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().flush();
                    response.getWriter().close();
                    return;
                }
            }
        } else {
//            if (request.getRequestURL().equals("http://localhost:8080/login/jwt")) {
                filterChain.doFilter(request, response);
                return;
//            }else {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                response.getWriter().write("Вы не ввели JWT токен");
//                response.setCharacterEncoding("UTF-8");
//                response.getWriter().flush();
//                response.getWriter().close();
//            }
        }
    }
}