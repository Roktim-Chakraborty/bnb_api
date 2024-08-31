package net.bnb_api.config;

import java.io.IOException;
import java.util.Optional;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.bnb_api.entity.AppUser;
import net.bnb_api.repository.AppUserRepository;
import net.bnb_api.service.JWTService;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(8, token.length() - 1);
            System.out.println("Token: " + token);
            String username = jwtService.getUsername(token);
            if (appUserRepository.findByUsername(username).isPresent()) {
                Optional<AppUser> appUser = appUserRepository.findByUsername(username);

                if (appUser.isPresent()) {
                    // authentication contains the token which contains the username and password
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            appUser.get(), null,
                            Collections.singleton(new SimpleGrantedAuthority(appUser.get().getRole())));
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
