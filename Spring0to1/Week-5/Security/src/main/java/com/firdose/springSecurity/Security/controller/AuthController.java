package com.firdose.springSecurity.Security.controller;

import com.firdose.springSecurity.Security.advices.ApiResponse;
import com.firdose.springSecurity.Security.dto.LoginDto;
import com.firdose.springSecurity.Security.dto.LoginResponseDto;
import com.firdose.springSecurity.Security.dto.SignupDto;
import com.firdose.springSecurity.Security.dto.UserDto;
import com.firdose.springSecurity.Security.exception.ResourceNotFoundException;
import com.firdose.springSecurity.Security.service.AuthService;
import com.firdose.springSecurity.Security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${jwt.env}")
    private String env;
    private final UserService userService;
    private final AuthService authService;


    @PostMapping(path = "/signUp")
    public ResponseEntity<UserDto> sighUp(@RequestBody SignupDto signupDto){
        UserDto user = userService.sighUp(signupDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/login")
    public  ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        LoginResponseDto loginResponseDto = authService.login(loginDto);
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(env.equals("prod"));
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(HttpServletRequest request) throws ResourceNotFoundException {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found in cookies"));
        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
