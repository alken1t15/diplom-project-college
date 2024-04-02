package kz.alken1t15.backratinglogcollege.security;

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
        System.out.println("авторизация");
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        Optional<kz.alken1t15.backratinglogcollege.entity.User> userRes = repositoryUser.findByLogin(login);
//        if(userRes.isEmpty())
//            throw new UsernameNotFoundException("Could not findUser with login = " + login);
//        kz.alken1t15.backratinglogcollege.entity.User user = userRes.get();
//        return new org.springframework.security.core.userdetails.User(
//                login,
//                user.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//    }
}