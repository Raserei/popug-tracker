package com.raserei.popugjira.authserv.rest;

import lombok.Data;

@Data
public class AuthRequest {

    private String login;

    private String pass;
}
