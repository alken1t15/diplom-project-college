package kz.alken1t15.backratinglogcollege.contoller;

import kz.alken1t15.backratinglogcollege.dto.LoginAuth;
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
    private RepositoryUser userRepo;
    private JWTUtil jwtUtil;
  //  private PasswordEncoder passwordEncoder;

    @PostMapping("/auth")
    public Map<String, Object> loginHandler(@RequestBody LoginAuth loginAuth) {

        System.out.println("test");
      //  UsernamePasswordAuthenticationToken authInputToken =
      //          new UsernamePasswordAuthenticationToken(email,password);
        String token = jwtUtil.generateToken(loginAuth.getLogin());
        return Collections.singletonMap("jwt-token", token);
    }

    @GetMapping("/test")
    public String loginHandler() {

        return "jwt-token";
    }
}