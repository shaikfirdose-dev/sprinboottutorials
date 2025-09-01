package com.firdose.springSecurity.Security.service;

import com.firdose.springSecurity.Security.dto.LoginDto;
import com.firdose.springSecurity.Security.dto.SignupDto;
import com.firdose.springSecurity.Security.dto.UserDto;
import com.firdose.springSecurity.Security.entity.User;
import com.firdose.springSecurity.Security.exception.ResourceNotFoundException;
import com.firdose.springSecurity.Security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("User not found with the username :"+username));
    }

    public UserDto sighUp(SignupDto signupDto) {

        Optional<User> userExists = userRepository.findByEmail(signupDto.getEmail());
        if(userExists.isPresent()){
            throw new BadCredentialsException("User already exists with email "+userExists.get().getEmail());
        }

        User toBeCreated = modelMapper.map(signupDto, User.class);
        toBeCreated.setPassword(passwordEncoder.encode(toBeCreated.getPassword()));
        User savedUser = userRepository.save(toBeCreated);
        return modelMapper.map(savedUser, UserDto.class);

    }

    public User getUserById(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with id "+userId));
        return user;
    }


}
