package net.bnb_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

        http.authorizeHttpRequests()
                .requestMatchers("/auth/login", "/auth/registerUser", "/auth/registerPropertyOwner", "/country/**",
                        "/city/**")
                .permitAll()
                .requestMatchers("/auth/registerPropertyManager").hasRole("ADMIN")
                .requestMatchers("/auth/registerProperty").hasAnyRole("OWNER", "ADMIN", "MANAGER")
                .anyRequest()
                .authenticated();

        http.csrf().disable().cors().disable();

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
