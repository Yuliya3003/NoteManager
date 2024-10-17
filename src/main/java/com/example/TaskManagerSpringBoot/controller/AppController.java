package com.example.TaskManagerSpringBoot.controller;

import com.example.TaskManagerSpringBoot.model.AuthenticationRequest;
import com.example.TaskManagerSpringBoot.service.AuthenticationService;
import com.example.TaskManagerSpringBoot.jwtToken.JwtResponse;
import com.example.TaskManagerSpringBoot.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntity user) throws Exception {
        authService.register(user);
        final String jwt = authService.login(new AuthenticationRequest(user.getUsername(), user.getPassword()));
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    // Логин пользователя и возврат JWT токена
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        final String jwt = authService.login(authenticationRequest);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Welcome to the dashboard!");
    }
}

