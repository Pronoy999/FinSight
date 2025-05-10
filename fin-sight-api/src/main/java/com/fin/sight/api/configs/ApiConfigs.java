package com.fin.sight.api.configs;

import com.fin.sight.api.service.UserService;
import com.fin.sight.api.repository.CredentialsRepository;
import com.fin.sight.api.repository.UserRepository;
import com.fin.sight.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.fin.sight.api"})
public class ApiConfigs implements WebMvcConfigurer {

    @Bean
    public JwtUtils jwtUtils(@Value("${jwt.secret.key}") String key) {
        return new JwtUtils(key);
    }

    @Bean
    public UserService userService(@Autowired UserRepository userRepository, @Autowired CredentialsRepository credentialsRepository, JwtUtils jwtUtils) {
        return new UserService(userRepository, credentialsRepository, jwtUtils);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // apply to all routes
                .allowedOrigins("http://localhost:4200") // allow Angular frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
