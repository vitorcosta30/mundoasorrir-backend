package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    SystemUser diretor;
    SystemUser employee;
    SystemUser manager;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.diretor = new SystemUser("diretor", "diretor@gmail.com","testpassword");
        this.diretor.setRoles(BaseRoles.DIRECTOR);
        this.diretor.setUserId(1L);
        this.manager = new SystemUser("manager", "manager@gmail.com","testpassoord");
        this.manager.setRoles(BaseRoles.MANAGER);
        this.manager.setUserId(2L);


        this.employee = new SystemUser("employee", "employee@gmail.com","testpassword");
        this.employee.setRoles(BaseRoles.EMPLOYEE);
        this.employee.setUserId(3L);
    }


    @Test
    void test(){
        when(userRepository.findByUsername("diretor")).thenReturn(Optional.of(this.diretor));
        assertEquals(userService.findUserByUsername("diretor"), this.diretor);

    }

    @Test
    void testUserNonExisting(){
        when(userRepository.findByUsername("diretor")).thenReturn(Optional.empty());
        assertNull(userService.findUserByUsername("nonexisting"));
    }

    @Test
    void testSave(){
        when(userRepository.save(any(SystemUser.class))).thenReturn(this.diretor);
        assertEquals(userService.save(this.diretor), this.diretor);
    }

    @Test
    void testSaveFails(){
        when(userRepository.save(any(SystemUser.class))).thenReturn(null);
        assertNull(userService.save(this.diretor));
    }

    @Test
    void testDeactivate(){
        when(userRepository.findByUsername("diretor")).thenReturn(Optional.of(this.diretor));

        when(userRepository.save(any(SystemUser.class))).thenReturn(this.diretor);
        assertFalse(userService.deactivateUser("diretor").isActive());
    }

    @Test
    void testActivate(){
        when(userRepository.findByUsername("diretor")).thenReturn(Optional.of(this.diretor));

        when(userRepository.save(any(SystemUser.class))).thenReturn(this.diretor);
        assertFalse(userService.deactivateUser("diretor").isActive());
        assertTrue(userService.activateUser("diretor").isActive());

    }
}