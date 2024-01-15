package com.nuzhd.taskmanagementsystem.service;

import com.nuzhd.taskmanagementsystem.dto.AuthResponseDto;
import com.nuzhd.taskmanagementsystem.dto.UserLoginRequest;
import com.nuzhd.taskmanagementsystem.dto.UserRegistrationRequest;
import com.nuzhd.taskmanagementsystem.exception.UsernameTakenException;
import com.nuzhd.taskmanagementsystem.model.MyUser;
import com.nuzhd.taskmanagementsystem.security.jwt.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final MyUserService myUserService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final PasswordEncoder encoder;

    public AuthenticationService(MyUserService myUserService, JWTUtils jwtUtils, AuthenticationManager authManager, PasswordEncoder encoder) {
        this.myUserService = myUserService;
        this.jwtUtils = jwtUtils;
        this.authManager = authManager;
        this.encoder = encoder;
    }

    public MyUser register(UserRegistrationRequest registrationRequest) throws UsernameTakenException {

        MyUser user = new MyUser(registrationRequest.username(),
                registrationRequest.email(),
                encoder.encode(registrationRequest.password())
        );

        return myUserService.save(user);
    }

    public AuthResponseDto login(UserLoginRequest loginRequest) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
        ));

        MyUser user = myUserService.findByUsername(loginRequest.username());

        String token = jwtUtils.generateJWT(user);
        return new AuthResponseDto(token);
    }

    public Long fetchUserIdFromAuthentication() {
        return (
                (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        ).getId();
    }
}
