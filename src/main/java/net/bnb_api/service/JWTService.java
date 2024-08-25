package net.bnb_api.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.annotation.PostConstruct;
import net.bnb_api.entity.AppUser;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;

    private final String USER_NAME = "username";

    @PostConstruct
    public void postConstruct() throws IllegalArgumentException, UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(AppUser user) {
        return JWT.create()
                .withClaim(USER_NAME, user.getUsername())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime))
                .sign(algorithm);
    }

    // Rashi with an issue is building to verify and claim the street
    public String getUsername(String token) {
        String username = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getClaim(USER_NAME)
                .asString();

        return username;
    }
}
