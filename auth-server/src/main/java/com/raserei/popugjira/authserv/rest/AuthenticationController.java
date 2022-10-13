package com.raserei.popugjira.authserv.rest;

import com.raserei.popugjira.authserv.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping(path = "/", consumes = "application/json", produces = "text/plain")
    public String authorize(@RequestBody AuthRequest authRequest) throws IllegalAccessException{
        return authService.getToken(authRequest.getUsername(), authRequest.getPassword());
    }

    @ExceptionHandler(value = IllegalAccessException.class)
    public ResponseEntity<HttpStatus> handleException(IllegalAccessException ex) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
