package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Requests.LoginRequest;
import com.mundoasorrir.mundoasorrirbackend.Auth.Requests.SignupRequest;
import com.mundoasorrir.mundoasorrirbackend.Config.SecurityConfig;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.MundoasorrirBackendApplication;
import com.mundoasorrir.mundoasorrirbackend.Repositories.RoleRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.UserRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.RefreshTokenService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserDetailsImpl;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.notNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MundoasorrirBackendApplication.class)
class AuthControllerTest {
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    @Autowired
    ObjectMapper objectMapper;
    private MockMvc mockMvc;
    @Mock
    private UserService userService;

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    private AuthUtils authUtils;








    @InjectMocks
    private AuthController authController;
    @Mock
    private RoleRepository roleRepository;


    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityConfig securityConfig;

    private LoginRequest request;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).addPlaceholderValue("mundoasorrir.app.frontend","http://some.url.com").build();
        userService.create("testUser","testEmail","testing", BaseRoles.DIRECTOR.getName());
        request.setPassword("testing");
        request.setUsername("testUser");

    }

    @Test
    void createUserWhileNotLoggged() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.highPermissions((HttpServletRequest)notNull())).thenReturn(false);
        Mockito.when(securityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        Mockito.when(jwtUtils.getCleanJwtCookie()).thenReturn(ResponseCookie.from("mundoasorrir", null).path("/api").build());
        Mockito.when(userService.getSystemRoles()).thenReturn(BaseRoles.systemRoles());
        mockMvc = MockMvcBuilders.standaloneSetup(authController).addPlaceholderValue("mundoasorrir.app.frontend","http://some.url.com").build();


        SignupRequest sRequest = new SignupRequest();

        sRequest.setUsername("testUser");
        sRequest.setRole(BaseRoles.DIRECTOR.getName());
        sRequest.setEmail("testUser@isep.pt");
        sRequest.setPassword("testUser");
        Mockito.when(userService.create(sRequest.getUsername(),sRequest.getEmail(),sRequest.getPassword(),BaseRoles.DIRECTOR.getName())).thenReturn(new SystemUser());


        mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON_UTF8).content(ow.writeValueAsString(sRequest))).andDo(print()).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));

    }


    @Test
    void createUserWhileLoggged() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.highPermissions((HttpServletRequest)notNull())).thenReturn(true);
        Mockito.when(securityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        Mockito.when(jwtUtils.getCleanJwtCookie()).thenReturn(ResponseCookie.from("mundoasorrir", null).path("/api").build());
        Mockito.when(userService.getSystemRoles()).thenReturn(BaseRoles.systemRoles());
        mockMvc = MockMvcBuilders.standaloneSetup(authController).addPlaceholderValue("mundoasorrir.app.frontend","http://some.url.com").build();


        SignupRequest sRequest = new SignupRequest();

        sRequest.setUsername("testUser");
        sRequest.setRole(BaseRoles.DIRECTOR.getName());
        sRequest.setEmail("testUser@isep.pt");
        sRequest.setPassword("testUser");
        Mockito.when(userService.create(sRequest.getUsername(),sRequest.getEmail(),sRequest.getPassword(),BaseRoles.DIRECTOR.getName())).thenReturn(new SystemUser());


        mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON_UTF8).content(ow.writeValueAsString(sRequest))).andDo(print()).andExpect(status().is(HttpStatus.CREATED.value()));

    }


    @Test
    void createUserWhileFailed() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.highPermissions((HttpServletRequest)notNull())).thenReturn(true);
        Mockito.when(securityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        Mockito.when(jwtUtils.getCleanJwtCookie()).thenReturn(ResponseCookie.from("mundoasorrir", null).path("/api").build());
        Mockito.when(userService.getSystemRoles()).thenReturn(BaseRoles.systemRoles());
        mockMvc = MockMvcBuilders.standaloneSetup(authController).addPlaceholderValue("mundoasorrir.app.frontend","http://some.url.com").build();


        SignupRequest sRequest = new SignupRequest();

        sRequest.setUsername("testUser");
        sRequest.setRole(BaseRoles.DIRECTOR.getName());
        sRequest.setEmail("testUser@isep.pt");
        sRequest.setPassword("testUser");
        Mockito.when(userService.create(sRequest.getUsername(),sRequest.getEmail(),sRequest.getPassword(),BaseRoles.DIRECTOR.getName())).thenReturn(null);


        mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON_UTF8).content(ow.writeValueAsString(sRequest))).andDo(print()).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    void isLoggedInTrue() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.isLoggedIn((HttpServletRequest)notNull())).thenReturn(true);

        Mockito.when(securityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        Mockito.when(jwtUtils.getCleanJwtCookie()).thenReturn(ResponseCookie.from("mundoasorrir", null).path("/api").build());
        Mockito.when(userService.getSystemRoles()).thenReturn(BaseRoles.systemRoles());
        mockMvc = MockMvcBuilders.standaloneSetup(authController).addPlaceholderValue("mundoasorrir.app.frontend","http://some.url.com").build();
        mockMvc.perform(get("/api/auth/isLoggedIn")).andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andExpect(content().string("true"));



    }
    @Test
    void isLoggedInFalse() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.isLoggedIn((HttpServletRequest)notNull())).thenReturn(false);

        Mockito.when(securityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        Mockito.when(jwtUtils.getCleanJwtCookie()).thenReturn(ResponseCookie.from("mundoasorrir", null).path("/api").build());
        Mockito.when(userService.getSystemRoles()).thenReturn(BaseRoles.systemRoles());
        mockMvc = MockMvcBuilders.standaloneSetup(authController).addPlaceholderValue("mundoasorrir.app.frontend","http://some.url.com").build();
        mockMvc.perform(get("/api/auth/isLoggedIn")).andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andExpect(content().string("false"));
    }
}