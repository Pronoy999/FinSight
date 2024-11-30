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

@Configuration
@ComponentScan(basePackages = {"com.fin.sight.api"})
public class ApiConfigs {

    @Bean
    public JwtUtils jwtUtils(@Value("${jwt.secret.key}") String key) {
        return new JwtUtils(key);
    }

    @Bean
    public UserService userService(@Autowired UserRepository userRepository, @Autowired CredentialsRepository credentialsRepository, JwtUtils jwtUtils) {
        return new UserService(userRepository, credentialsRepository, jwtUtils);
    }
}
