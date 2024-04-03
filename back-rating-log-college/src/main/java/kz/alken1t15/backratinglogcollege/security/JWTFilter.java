package kz.alken1t15.backratinglogcollege.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.alken1t15.backratinglogcollege.dto.LoginAuth;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    @Value("${server.login}")
    private String serverLogin;
    @Value("${server.password}")
    private String serverPassword;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null) {
            if (headerAuth.startsWith("Bearer ")) {
                String token = headerAuth.substring(7);
                LoginAuth loginAuth = jwtUtil.validateTokenAndRetrieveSubject(token);
                String login = loginAuth.getLogin();
                String password = loginAuth.getLogin();
                SecurityContext securityContext = SecurityContextHolder.getContext();
                String anonymous = securityContext.getAuthentication().getName();
                if (anonymous.equals("anonymousUser")) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login,password);
                    System.out.println(usernamePasswordAuthenticationToken.isAuthenticated());
                    System.out.println("Авторизация");
                    System.out.println(usernamePasswordAuthenticationToken.getName());
                    Principal principal = (Principal) usernamePasswordAuthenticationToken.getPrincipal();
                    System.out.println(principal.getName());
                    if (serverLogin.equals(login) && serverPassword.equals(password)) {
//                        Authentication authentication = new TestingAuthenticationToken(serverLogin, serverPassword, "ROLE_USER");
//                        securityContext.setAuthentication(authentication);
//                        SecurityContextHolder.setContext(securityContext);
                        filterChain.doFilter(request, response);
                    } else {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.getWriter().write("Не правильный JWT токен");
                        response.getWriter().flush();
                        response.getWriter().close();
                        return;
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}