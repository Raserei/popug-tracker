package com.raserei.popugjira.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class TrackerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackerServerApplication.class, args);
    }

}
