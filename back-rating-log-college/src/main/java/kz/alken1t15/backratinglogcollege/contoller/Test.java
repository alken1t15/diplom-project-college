package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.LoginAuth;
import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import kz.alken1t15.backratinglogcollege.security.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class Test {
    private RepositoryUser repositoryUser;
    private JWTUtil jwtUtil;

    @PostMapping("/auth")
    public Map<String, Object> loginHandler(@RequestBody LoginAuth loginAuth) {
        User user = repositoryUser.findByLoginAndPassword(loginAuth.getLogin(), loginAuth.getPassword()).orElse(null);
        if (user != null) {
            String token = jwtUtil.generateToken(loginAuth.getLogin(),loginAuth.getPassword());
            return Collections.singletonMap("jwt-token", token);
        } else {
            return Collections.singletonMap("Ошибка", "ошибка");
        }
    }

    @GetMapping("/test")
    public String loginHandler() {

        return "jwt-token";
    }
}