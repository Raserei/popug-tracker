package com.raserei.popugjira.authserv.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.raserei.popugjira.authserv.domain.UserAccount;
import com.raserei.popugjira.authserv.rest.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserService userService;

    public String getToken (String email, String password) throws IllegalAccessException {
        UserAccount account = userService.getUser(email, password);
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(account.getId())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new IllegalStateException();
        }
    }

    public String validateToken (String token) throws JWTVerificationException{
            Algorithm algorithm = Algorithm.HMAC256("secret"); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
