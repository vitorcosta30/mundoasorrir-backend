package com.mundoasorrir.mundoasorrirbackend;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import  org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class MundoasorrirBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MundoasorrirBackendApplication.class, args);
    }
/*
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }*/
/*
    @RequestMapping("/token")
    public Map<String,String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }*/

}
