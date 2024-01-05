package com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.BaseEventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserGroupTest {
    SystemUser diretor;
    SystemUser employee;
    SystemUser manager;
    List<SystemUser> users;
    UserGroup userGroup;



    @BeforeEach
    void setUp()  {
        this.diretor = new SystemUser("diretor", "diretor@gmail.com","testpassword");
        this.diretor.setRoles(BaseRoles.DIRECTOR);
        this.manager = new SystemUser("manager", "manager@gmail.com","testpassoord");
        this.manager.setRoles(BaseRoles.MANAGER);
        this.employee = new SystemUser("employee", "employee@gmail.com","testpassword");
        this.employee.setRoles(BaseRoles.EMPLOYEE);
        this.users = new ArrayList<>();
        users.add(this.diretor);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        userGroup = new UserGroup("Testing group", this.diretor,"test1");
    }
    @Test
    void isCreatorGroup(){
        Assertions.assertTrue(this.userGroup.isUserCreator(this.diretor));

    }
    @Test
    void isCreatorInGroup(){
        Assertions.assertTrue(this.userGroup.isUserInGroup(this.diretor));

    }
    @Test
    void isUserNotInGroup(){
        Assertions.assertFalse(this.userGroup.isUserInGroup(this.employee));

    }
    @Test
    void isUserInGroupAfterAdding(){
        Assertions.assertFalse(this.userGroup.isUserInGroup(this.employee));
        this.userGroup.addUser(this.employee);
        Assertions.assertTrue(this.userGroup.isUserInGroup(this.employee));

    }

    @Test
    void isUserInGroupAfterRemoving(){
        Assertions.assertFalse(this.userGroup.isUserInGroup(this.employee));
        this.userGroup.addUser(this.employee);
        Assertions.assertTrue(this.userGroup.isUserInGroup(this.employee));
        this.userGroup.removeUser(this.employee);
        Assertions.assertFalse(this.userGroup.isUserInGroup(this.employee));

    }


}