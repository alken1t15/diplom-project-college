package kz.alken1t15.backratinglogcollege.contoller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
<<<<<<< HEAD
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
=======
>>>>>>> bfcfa1df1887092368b3b4b5b8885d2881b842d0

@Configuration
public class CorsConfig  {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Разрешить запросы только с этого домена
<<<<<<< HEAD
        config.addAllowedHeader(""); // Разрешить все заголовки
        config.addAllowedMethod(""); // Разрешить все методы
=======
        config.addAllowedHeader("*"); // Разрешить все заголовки
        config.addAllowedMethod("*"); // Разрешить все методы
>>>>>>> bfcfa1df1887092368b3b4b5b8885d2881b842d0
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}