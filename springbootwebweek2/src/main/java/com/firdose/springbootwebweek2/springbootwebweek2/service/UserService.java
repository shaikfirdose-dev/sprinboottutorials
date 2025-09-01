package com.firdose.springbootwebweek2.springbootwebweek2.service;
import com.firdose.springbootwebweek2.springbootwebweek2.dto.SignUpDto;
import com.firdose.springbootwebweek2.springbootwebweek2.dto.UserDto;
import com.firdose.springbootwebweek2.springbootwebweek2.entity.User;
import com.firdose.springbootwebweek2.springbootwebweek2.exceptions.ResourceNotFoundException;
import com.firdose.springbootwebweek2.springbootwebweek2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResolutionException("User not found with id :"+username));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> userExists = userRepository.findByEmail(signUpDto.getEmail());
        if(userExists.isPresent()){
            throw new ResolutionException("User already exists with email "+signUpDto.getEmail());
        }
        User user = modelMapper.map(signUpDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));  //password should be encrypted
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }


    public User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id "+userId));
        return user;

    }
}
