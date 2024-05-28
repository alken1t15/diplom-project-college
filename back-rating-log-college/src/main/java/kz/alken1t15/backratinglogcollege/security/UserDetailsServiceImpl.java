package kz.alken1t15.backratinglogcollege.security;

import kz.alken1t15.backratinglogcollege.entity.User;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService
{

    private RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = repositoryUser.findByLogin(username).orElse(null);
     return new UserDetailsImp(user);
    }
}