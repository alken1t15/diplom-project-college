package kz.alken1t15.backratinglogcollege.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.alken1t15.backratinglogcollege.dto.LoginAuth;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    private Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RepositoryUser repositoryUser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        if (headerAuth != null) {
            String url = request.getRequestURL().toString();
            if (url.equals("http://localhost:8080/login/jwt")) {
                if (request.getMethod().equals("OPTIONS")) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    filterChain.doFilter(request, response);
                }
                return;
            }
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
                    User user = repositoryUser.findByLogin(login).orElseThrow();
                    if (user.getJwt()==null){
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Не правильный JWT токен");
                        response.setCharacterEncoding("UTF-8");
                        return;
                    }
                    if (!user.getJwt().equals(token)){
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Истек срок JWT токен");
                        response.setCharacterEncoding("UTF-8");
                        return;
                    }
                   else if (date.isAfter(LocalDate.now())) {
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
            String url = request.getRequestURL().toString();
            if (url.equals("http://localhost:8080/login/jwt")) {
                if (request.getMethod().equals("OPTIONS")) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    filterChain.doFilter(request, response);
                }
                return;
            }
            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Вы не ввели JWT токен");
                response.setCharacterEncoding("UTF-8");
            }
        }
    }
}