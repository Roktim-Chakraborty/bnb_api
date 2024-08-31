package net.bnb_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.bnb_api.entity.AppUser;
import net.bnb_api.exception.UserExistsException;
import net.bnb_api.payload.JWTToken;
import net.bnb_api.payload.LoginDTO;
import net.bnb_api.repository.AppUserRepository;
import net.bnb_api.service.AppUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/registerUser")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser appUser) {
        if (appUserRepository.findByUsernameOrEmail(appUser.getUsername(), appUser.getEmail()).isPresent()) {
            throw new UserExistsException("User already exists");
        }
        appUser.setRole("ROLE_USER");
        AppUser savedUser = appUserService.save(appUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @PostMapping("/registerPropertyOwner")
    public ResponseEntity<AppUser> registerPropertyOwner(@RequestBody AppUser appUser) {
        if (appUserRepository.findByUsernameOrEmail(appUser.getUsername(), appUser.getEmail()).isPresent()) {
            throw new UserExistsException("User already exists");
        }
        appUser.setRole("ROLE_OWNER");
        AppUser savedUser = appUserService.save(appUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/registerPropertyManager")
    public ResponseEntity<AppUser> registerPropertyManager(@RequestBody AppUser appUser) {
        if (appUserRepository.findByUsernameOrEmail(appUser.getUsername(), appUser.getEmail()).isPresent()) {
            throw new UserExistsException("User already exists");
        }
        appUser.setRole("ROLE_MANAGER");
        AppUser savedUser = appUserService.save(appUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        JWTToken jwtToken = new JWTToken();
        String token = appUserService.login(loginDTO);

        if (token != null) {
            jwtToken.setTokenType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

    }
}
