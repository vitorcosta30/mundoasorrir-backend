package com.mundoasorrir.mundoasorrirbackend.Auth;

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
    private final SystemUser firstUser = new SystemUser("firstRunUser", "firstrun@gmail.com","FirstRunUserPassword");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(env.containsProperty(bootstrap) && env.getProperty(bootstrap).equals("true")){
            logger.warn("First run option was activated, access first run user profile and create user profiles and follow instructions on configuration manual!!");
            if(this.userDetailsService.existsByUsername(this.firstUser.getUsername())){
                this.userDetailsService.activateUser(this.firstUser.getUsername());
                logger.warn("First run user was activated!! Reminder to do restart the back end after doing what needs to be done with this user!!");
            }else{
                this.userDetailsService.create(this.firstUser.getUsername(),this.firstUser.getEmail(),this.firstUser.getPassword(), BaseRoles.DIRECTOR.getName());
                logger.warn("First run user was created, reminder to, after creating other users, too shutdown the backend and start it again, but in the normal way for the user to be deleted, otherwise it might become a vulnerabilty!!");
            }
        }else{
            if(this.userDetailsService.existsByUsername(this.firstUser.getUsername())){
                if(this.userDetailsService.isUserActiveUsername(this.firstUser.getUsername())){
                    this.userDetailsService.deactivateUser(this.firstUser.getUsername());
                    logger.info("First run user was deactivated!!");
                }
                this.userDetailsService.delete(firstUser);
                logger.info("First run user was deleted!!");
            }
        }
    }

}
