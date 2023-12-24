package com.mundoasorrir.mundoasorrirbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MundoasorrirBackendApplication {

    public static void main(String[] args) {

        for(String arg:args) {
            System.out.println(arg);
        }
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
