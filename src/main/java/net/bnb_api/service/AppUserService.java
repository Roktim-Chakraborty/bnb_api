package net.bnb_api.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import net.bnb_api.entity.AppUser;
import net.bnb_api.payload.LoginDTO;
import net.bnb_api.repository.AppUserRepository;

@Service
public class AppUserService {
    
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JWTService jwtService;

    public AppUser save(AppUser appUser) {
        appUser.setPassword(BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(4)));
        return appUserRepository.save(appUser);
    }

    public String login(LoginDTO loginDTO) {
        Optional<AppUser> appUser = appUserRepository.findByUsernameOrEmail(loginDTO.getUsername(), loginDTO.getUsername());

        if(appUser.isPresent()) {
            if(BCrypt.checkpw(loginDTO.getPassword(), appUser.get().getPassword())){
                return jwtService.generateToken(appUser.get());
            } 
        }
        return null;
    }
}
