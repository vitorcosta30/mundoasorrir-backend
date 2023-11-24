package com.mundoasorrir.mundoasorrirbackend.Controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Requests.SignupRequest;
import com.mundoasorrir.mundoasorrirbackend.Auth.Requests.LoginRequest;

import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.UserInfoResponse;
import com.mundoasorrir.mundoasorrirbackend.Domain.RefreshToken;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Exception.TokenRefreshException;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseEvent;
import com.mundoasorrir.mundoasorrirbackend.Repositories.RoleRepository;
import com.mundoasorrir.mundoasorrirbackend.Repositories.UserRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.RefreshTokenService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<Boolean> isLoggedIn(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(!jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request)).isEmpty());
    }
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser usr = userRepository.findByUsername(username).get();
        List<String> roles = usr.getSystemRole().stream()
                .map(item -> item.getName())
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(usr.getUserId(),
                usr.getUsername(),
                usr.getEmail(),
                roles));



    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        SystemUser systemUser = new SystemUser(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        String strRoles = signUpRequest.getRole();
        Role[] roles = BaseRoles.systemRoles();
        List<Role> rolesSaved = roleRepository.findAll();
        if(strRoles == null ){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Role is null!"));
        }
        for(int i = 0 ; i < roles.length ; i++){
            if( strRoles.equals(roles[i].getName())){
                logger.warn(strRoles + " - " +roles[i].getName());

                    for(int x = 0 ; x < rolesSaved.size();x++){
                        if(rolesSaved.get(x).getName().equals(roles[i].getName())){
                            logger.warn(roles[i].getName() + " - " +rolesSaved.get(x));

                            systemUser.setRoles(rolesSaved.get(x));
                            userRepository.save(systemUser);
                            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
                        }
                    }
                systemUser.setRoles(roles[i]);
                roleRepository.save(roles[i]);
                userRepository.save(systemUser);
                return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            }
        }


        return ResponseEntity.badRequest().body(new MessageResponse("Error: Role does not exist!!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(UserDetailsImpl.build(user));

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(new MessageResponse("Token is refreshed successfully!"));
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is empty!"));
    }

}
