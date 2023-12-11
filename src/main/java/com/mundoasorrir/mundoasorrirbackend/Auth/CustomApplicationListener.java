package com.mundoasorrir.mundoasorrirbackend.Auth;

import com.mundoasorrir.mundoasorrirbackend.Controllers.EventsController;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CustomApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private Environment env;
    @Autowired
    UserService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(CustomApplicationListener.class);

    private final String bootstrap = "bootstrap-users";
    private SystemUser firstUser = new SystemUser("firstRunUser", "firstrun@gmail.com","deveSerDesativadoAposLogout");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(env.containsProperty(bootstrap) && env.getProperty(bootstrap).equals("true")){
            System.out.println("1");

            if(this.userDetailsService.existsByUsername(this.firstUser.getUsername())){
                System.out.println("2");

            }else{
                System.out.println("3");

                this.userDetailsService.create(this.firstUser.getUsername(),this.firstUser.getEmail(),this.firstUser.getPassword(), BaseRoles.DIRECTOR.getName());
            }
        }else{
            System.out.println("4");

            if(this.userDetailsService.existsByUsername(this.firstUser.getUsername())){
                System.out.println("5");
                if(this.userDetailsService.isUserActiveUsername(this.firstUser.getUsername())){
                    this.userDetailsService.deactivateUser(this.firstUser.getUsername());
                }
                this.userDetailsService.delete(firstUser);

            }
        }
        System.out.println("6");
        // run your code
    }

}