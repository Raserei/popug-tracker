package com.raserei.popugjira.authserv.rest;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserCreationDto {

    private String username;

    private String password;

    private String email;

    private String name;

    private String surname;

    private List<String> userRoles;
}
