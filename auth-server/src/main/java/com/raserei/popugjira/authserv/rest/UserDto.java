package com.raserei.popugjira.authserv.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class UserDto {

    private String id;

    private String username;

}
