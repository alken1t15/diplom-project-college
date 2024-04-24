package kz.alken1t15.backratinglogcollege.contoller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig  {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Разрешить запросы только с этого домена
        config.addAllowedHeader("*"); // Разрешить все заголовки
        config.addAllowedMethod("*"); // Разрешить все методы
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}