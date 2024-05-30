package kz.alken1t15.backratinglogcollege.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private JWTFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()).addFilterBefore(filter, AuthorizationFilter.class)
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/login/jwt")
                        .permitAll()
                        .requestMatchers("/teacher/**").hasRole("teacher")
                        .requestMatchers("/student/**").hasRole("student")
                        .requestMatchers("/create/**").hasRole("admin")
                        .anyRequest().authenticated())
                .build();
    }
}