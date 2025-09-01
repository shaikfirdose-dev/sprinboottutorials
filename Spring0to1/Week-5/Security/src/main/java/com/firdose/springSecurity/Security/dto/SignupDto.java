package com.firdose.springSecurity.Security.dto;

import com.firdose.springSecurity.Security.entity.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupDto {

    private String email;
    private String name;
    private String password;
    private Set<Role> roles;
}
