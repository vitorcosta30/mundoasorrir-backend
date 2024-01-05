package com.mundoasorrir.mundoasorrirbackend.Domain.User;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.BaseEventType;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemUserTest {
    SystemUser diretor;
    SystemUser employee;
    SystemUser manager;
    List<SystemUser> users;

    @BeforeEach
    void setUp() throws ParseException {
        this.diretor = new SystemUser("diretor", "diretor@gmail.com","testpassword");
        this.diretor.setRoles(BaseRoles.DIRECTOR);
        this.manager = new SystemUser("manager", "manager@gmail.com","testpassoord");
        this.manager.setRoles(BaseRoles.MANAGER);
        this.employee = new SystemUser("employee", "employee@gmail.com","testpassword");
        this.employee.setRoles(BaseRoles.EMPLOYEE);
        this.users = new ArrayList<>();
        users.add(this.diretor);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");



        this.diretor.getEvent().add(new Event(this.users,dateFormat.parse("2023-12-15"),dateFormat.parse("2023-12-30"), BaseEventType.VACATION));
        this.diretor.getEvent().add(new Event(this.users,dateFormat.parse("2024-12-15"),dateFormat.parse("2025-01-03"), BaseEventType.VACATION));
    }

    @Test
    void testRoleDiretor(){
        assertEquals(this.diretor.getRole(), BaseRoles.DIRECTOR);
    }
    @Test
    void testRoleEmployee(){
        assertEquals(this.employee.getRole(), BaseRoles.EMPLOYEE);
    }
    @Test
    void testRoleManager(){
        assertEquals(this.manager.getRole(), BaseRoles.MANAGER);
    }


    @Test
    void testIsOnVacationTrue(){
        Assertions.assertTrue(this.diretor.isUserOnVacationInMonth(12,2023));
    }

    @Test
    void testIsOnVacationTrueMultiyear(){
        Assertions.assertTrue(this.diretor.isUserOnVacationInMonth(12,2024));
        Assertions.assertTrue(this.diretor.isUserOnVacationInMonth(1,2025));

    }

    @Test

    void testIsOnVacationFalse(){
        Assertions.assertFalse(this.diretor.isUserOnVacationInMonth(11,2023));
        Assertions.assertFalse(this.diretor.isUserOnVacationInMonth(2,2024));
    }

    @Test
    void testIsActive(){
        Assertions.assertTrue(this.diretor.isActive());
    }
    @Test
    void testIsInactive(){
        Assertions.assertTrue(this.diretor.isActive());
        this.diretor.deactivate();
        Assertions.assertFalse(this.diretor.isActive());

    }
    @Test
    void testActivate(){
        Assertions.assertTrue(this.diretor.isActive());
        this.diretor.deactivate();
        Assertions.assertFalse(this.diretor.isActive());
        this.diretor.activate();
        Assertions.assertTrue(this.diretor.isActive());

    }

}