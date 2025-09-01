package com.firdose.springSecurity.Security.service;

import com.firdose.springSecurity.Security.dto.LoginDto;
import com.firdose.springSecurity.Security.dto.LoginResponseDto;
import com.firdose.springSecurity.Security.entity.User;
import com.firdose.springSecurity.Security.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {
        log.info("Authenticating user with email: {}", loginDto.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        log.info("User authenticated successfully with id: {}", user.getId());
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        log.info("Generating session for user with id: {}", user.getId());
        sessionService.generateSession(user, refreshToken);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public LoginResponseDto refreshToken(String refreshToken) throws ResourceNotFoundException {
        log.info("Refreshing token for refresh token: {}", refreshToken);
        Long userId = jwtService.generateUserIdFromToken(refreshToken);
        log.info("User ID extracted from refresh token: {}", userId);
        sessionService.validateSession(refreshToken);
        log.info("Session validated for user with id: {}", userId);
        User user = userService.getUserById(userId);
        String accessToken = jwtService.generateAccessToken(user);
        log.info("Generated new access token for user with id: {}", userId);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
