package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceUsers {
    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;

    public User getUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return repositoryUser.findByLogin(securityContext.getAuthentication().getName()).orElseThrow();
    }


    public User getUser(String login){
        return repositoryUser.findByLogin(login).orElse(null);
    }

    public Long save(String login,String password,String role) {
        User user = repositoryUser.save(new User(login,password,role));
        System.out.println(user.getId());
        return user.getId();
    }
}