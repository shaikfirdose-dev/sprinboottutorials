package com.firdose.springbootwebweek2.springbootwebweek2.controller;


import com.firdose.springbootwebweek2.springbootwebweek2.advices.APIResponse;
import com.firdose.springbootwebweek2.springbootwebweek2.dto.LoginDto;
import com.firdose.springbootwebweek2.springbootwebweek2.dto.SignUpDto;
import com.firdose.springbootwebweek2.springbootwebweek2.dto.UserDto;
import com.firdose.springbootwebweek2.springbootwebweek2.service.AuthService;
import com.firdose.springbootwebweek2.springbootwebweek2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping(path = "/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto user = userService.signUp(signUpDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<APIResponse<?>> login(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }
}
