package kz.alken1t15.backratinglogcollege.contoller;

import ch.qos.logback.classic.LoggerContext;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.alken1t15.backratinglogcollege.dto.LoginAuth;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import kz.alken1t15.backratinglogcollege.security.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class ControllerMain {
    private Logger logger = LoggerFactory.getLogger(ControllerMain.class);
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private final AuthenticationManager authenticationManager;
//    @Autowired
//    private ControllerStudent controllerStudent;

    public ControllerMain(RepositoryUser repositoryUser, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.repositoryUser = repositoryUser;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/jwt")
    public Map<String, Object> loginHandler(@RequestBody LoginAuth loginAuth) {
        User user = repositoryUser.findByLoginAndPassword(loginAuth.getLogin(), loginAuth.getPassword()).orElse(null);
        if (user != null) {
            String token = jwtUtil.generateToken(loginAuth.getLogin(),loginAuth.getPassword());
            return Collections.singletonMap("jwt-token", token);
        } else {
            return Collections.singletonMap("Ошибка", "ошибка");
        }
    }

    @PostMapping("/auth")
    public String authorizationUser(@RequestBody LoginAuth loginAuth, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        logger.info(String.format("Авторизация пользваоетяля: логин: %s пароль: %s",loginAuth.getLogin(),loginAuth.getPassword()));
        Authentication token  = UsernamePasswordAuthenticationToken.unauthenticated(loginAuth.getLogin(), loginAuth.getPassword());
        System.out.println(token );
        Authentication authenticationResponse = this.authenticationManager.authenticate(token);
        System.out.println(authenticationResponse);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationResponse);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context,request,response);
//        securityContext.setAuthentication(authenticationResponse);
//        SecurityContextHolder.setContext(securityContext);
//      String str =  HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

        return "jwt-token";
    }
}