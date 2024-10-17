package com.example.TaskManagerSpringBoot.service;

import com.example.TaskManagerSpringBoot.model.AuthenticationRequest;
import com.example.TaskManagerSpringBoot.JwtTokenUtil;
import com.example.TaskManagerSpringBoot.model.UserEntity;
import com.example.TaskManagerSpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    // Метод для регистрации пользователя
    public void register(UserEntity registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Пользователь уже существует");
        }

        // Создаем нового пользователя и сохраняем его
        UserEntity newUser = new UserEntity();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(newUser);
    }

    // Метод для логина пользователя и возврата JWT токена
    public String login(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new Exception("Неправильный логин или пароль", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        // Генерация JWT токена
        return jwtTokenUtil.generateToken(userDetails);
    }
}

