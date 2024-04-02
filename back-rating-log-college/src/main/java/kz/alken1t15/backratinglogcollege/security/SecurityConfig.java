package kz.alken1t15.backratinglogcollege.security;

import jakarta.servlet.http.HttpServletResponse;
import kz.alken1t15.backratinglogcollege.repository.RepositoryUser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private JWTFilter filter;
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
                       .requestMatchers(HttpMethod.POST,"/login/auth").permitAll()
                       .anyRequest().authenticated()
                ).addFilterBefore(filter, AuthorizationFilter.class).userDetailsService(userDetailsService).build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
