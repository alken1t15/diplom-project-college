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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/login")
public class ControllerMain {
    private Logger logger = LoggerFactory.getLogger(ControllerMain.class);
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final RepositoryUser repositoryUser;


    public ControllerMain(JWTUtil jwtUtil, AuthenticationManager authenticationManager,RepositoryUser repositoryUser) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.repositoryUser = repositoryUser;
    }

    @PostMapping("/jwt")
    public Map<String, Object> loginHandler(@RequestBody LoginAuth loginAuth) {
        if (StringUtils.isBlank(loginAuth.getLogin()) || StringUtils.isBlank(loginAuth.getPassword())) {
            throw new BadCredentialsException("Одно из полей пустое");
        }
        logger.info(String.format("Авторизация пользваоетяля: логин: %s пароль: %s", loginAuth.getLogin(), loginAuth.getPassword()));
        Authentication token = UsernamePasswordAuthenticationToken.unauthenticated(loginAuth.getLogin(), loginAuth.getPassword());
        Authentication authenticationResponse;
        try {
            authenticationResponse = this.authenticationManager.authenticate(token);
        }catch (NullPointerException e){
            return null;
        }
        User user = repositoryUser.findByLogin(loginAuth.getLogin()).orElseThrow();
        logger.info(String.format("Пользователь который хочет получить jwt токен: %s", authenticationResponse));
        String jwt = jwtUtil.generateToken(loginAuth.getLogin(), loginAuth.getPassword());
        user.setJwt(jwt);
        repositoryUser.save(user);
        logger.info(String.format("JWT: %s", jwt));
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("jwt-token", jwt);
        hashMap.put("role",user.getRole());
        return hashMap;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity errorAuthentication(BadCredentialsException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/auth")
    public String authorizationUser(@RequestBody LoginAuth loginAuth, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        logger.info(String.format("Авторизация пользваоетяля: логин: %s пароль: %s", loginAuth.getLogin(), loginAuth.getPassword()));
        Authentication token = UsernamePasswordAuthenticationToken.unauthenticated(loginAuth.getLogin(), loginAuth.getPassword());
//        Authentication authenticationResponse = this.authenticationManager.authenticate(token);
//        System.out.println(authenticationResponse);
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authenticationResponse);
//        SecurityContextHolder.setContext(context);
//        securityContextRepository.saveContext(context,request,response);

        return "jwt-token";
    }
}