package com.firdose.springbootwebweek2.springbootwebweek2.service;

import com.firdose.springbootwebweek2.springbootwebweek2.advices.APIResponse;
import com.firdose.springbootwebweek2.springbootwebweek2.dto.LoginDto;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public APIResponse<String> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new APIResponse<>(token);

    }
}
