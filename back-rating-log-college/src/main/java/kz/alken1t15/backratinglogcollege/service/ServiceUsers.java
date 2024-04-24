package kz.alken1t15.backratinglogcollege.service;

import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceUsers {
    private final RepositoryUser repositoryUser;

    public User getUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return repositoryUser.findByLogin(securityContext.getAuthentication().getName()).orElseThrow();
    }
}