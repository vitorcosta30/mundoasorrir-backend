package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTestAPI {
    @Mock
    private UserService userService;

    @Mock
    JwtUtils jwtUtils;
    @Mock
    private AuthUtils authUtils;

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    SystemUser diretor;
    SystemUser employee;
    SystemUser manager;
    List<SystemUser> users;

    List<UserDTO> usersDTO;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).addPlaceholderValue("mundoasorrir.app.frontend","http://some.url.com").build();
        this.diretor = new SystemUser("diretor", "diretor@gmail.com","testpassword");
        this.diretor.setRoles(BaseRoles.DIRECTOR);
        this.diretor.setUserId(1L);
        this.manager = new SystemUser("manager", "manager@gmail.com","testpassoord");
        this.manager.setRoles(BaseRoles.MANAGER);
        this.manager.setUserId(2L);


        this.employee = new SystemUser("employee", "employee@gmail.com","testpassword");
        this.employee.setRoles(BaseRoles.EMPLOYEE);
        this.employee.setUserId(3L);

        this.users = new ArrayList<>();
        users.add(this.diretor);
        users.add(this.manager);
        users.add(this.employee);

        this.usersDTO = UserMapper.toDTO(this.users);


    }
    @Test
    void getUsersAuthenticated() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.lowPermissions((HttpServletRequest)notNull())).thenReturn(true);
        Mockito.when(userService.findAll()).thenReturn(this.users);
        mockMvc.perform(get("/api/users/getUsers")).andDo(print()).andExpect(status().is(HttpStatus.OK.value()));
    }


    @Test
    void getUsersNotAuthenticated() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.lowPermissions((HttpServletRequest)notNull())).thenReturn(false);
        Mockito.when(userService.findAll()).thenReturn(this.users);
        mockMvc.perform(get("/api/users/getUsers")).andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }



    @Test
    void getExistingUserNotAuthenticated() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.lowPermissions((HttpServletRequest)notNull())).thenReturn(false);
        Mockito.when(userService.findUserByUsername(this.diretor.getUsername())).thenReturn(this.diretor);
        mockMvc.perform(get("/api/users/getInfo/diretor")).andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    void getNonExistingUser() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.lowPermissions((HttpServletRequest)notNull())).thenReturn(true);
        Mockito.when(userService.findUserByUsername(this.diretor.getUsername())).thenReturn(null);
        mockMvc.perform(get("/api/users/getInfo/nonexisting")).andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }



    @AfterEach
    void tearDown(){

    }
}