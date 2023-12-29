package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Requests.LoginRequest;
import com.mundoasorrir.mundoasorrirbackend.Auth.Requests.SignupRequest;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.UserInfoResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.ChangePassword.ChangeMyPasswordDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.ChangePassword.ChangePasswordDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.RefreshToken;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Exception.TokenRefreshException;
import com.mundoasorrir.mundoasorrirbackend.Message.ErrorMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.SuccessMessage;
import com.mundoasorrir.mundoasorrirbackend.Repositories.RoleRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.RefreshTokenService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserDetailsImpl;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
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

import java.util.List;
import java.util.stream.Collectors;


/**
 *
 */
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;

    /**
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if(this.userService.existsByUsername(loginRequest.getUsername())) {
            if (!this.userService.isUserActiveUsername(loginRequest.getUsername())) {
                return ResponseEntity.status(401).body(ErrorMessage.ACCOUNT_DEACTIVATED);
            }
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        logger.info("User "+ userDetails.getUsername() +  " logged in!");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    /**
     *
     * @param request
     * @return
     */

    @GetMapping("/isLoggedIn")
    public ResponseEntity<Boolean> isLoggedIn(HttpServletRequest request) {
        try {
            SystemUser user = authUtils.getUserFromRequest(request);
            if((!jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request)).isEmpty()) && user != null && user.isActive()){
                return ResponseEntity.status(HttpStatus.OK).body(true);
            }else{
                ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

                return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(false);
            }

        }catch(Exception e){
            ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(false);
        }

    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        UserDTO usr = UserMapper.toDTO(userService.findUserByUsername(username));
        return ResponseEntity.status(HttpStatus.OK).body(usr);
    }

    /**
     *
     * @param signUpRequest
     * @return
     */

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(ErrorMessage.USERNAME_IN_USE);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(ErrorMessage.EMAIL_IN_USE);
        }

        if (this.userService.create(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRole()) != null) {
            logger.info("User " + signUpRequest.getUsername() + " was created!");
            return ResponseEntity.ok().body(SuccessMessage.USER_CREATED);
        } else {
            return ResponseEntity.badRequest().body(ErrorMessage.ROLE_DOES_NOT_EXIST);
        }
    }

    /**
     *
     * @param username
     * @param newPassword
     * @param request
     * @return
     */

    @PatchMapping("/changePassword/{username}")
    public ResponseEntity<?> changePassword(@PathVariable(name = "username") String username,@RequestBody ChangePasswordDTO newPassword, HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);

        }
        if(!username.equals(newPassword.getUsername())){
            return ResponseEntity.badRequest().body(ErrorMessage.ILLEGAL_REQUEST);
        }
        SystemUser user = this.userService.findUserByUsername(newPassword.getUsername());
        user.setPassword(encoder.encode(newPassword.getNewPassword()));
        try{
            this.userService.save(user);
            return ResponseEntity.ok().body(SuccessMessage.PASSWORD_WAS_UPDATED);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);

        }

    }

    /**
     *
     * @param newPassword
     * @param request
     * @return
     */
    @PatchMapping("/changeMyPassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangeMyPasswordDTO newPassword, HttpServletRequest request){
        SystemUser user = this.authUtils.getUserFromRequest(request);
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);

        }
        if(!this.encoder.matches(newPassword.getOldPassword(),user.getPassword())){
            logger.info(newPassword.getOldPassword()+  "-"+ user.getPassword());

            return ResponseEntity.status(403).body(ErrorMessage.WRONG_PASSWORD);
        }
        if(!newPassword.getReNewPassword().equals(newPassword.getNewPassword())){
            return ResponseEntity.status(401).body(ErrorMessage.PASSWORDS_DONT_MATCH);
        }
        user.setPassword(this.encoder.encode(newPassword.getNewPassword()));
        if(this.userService.save(user) != null){
            return ResponseEntity.ok().body(SuccessMessage.PASSWORD_WAS_UPDATED);
        }else{
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);

        }

    }

    /**
     *
     * @param idUser
     * @param updatedUser
     * @param request
     * @return
     */
    @PutMapping(value = "/updateUser/{idUser}")
    public ResponseEntity<?> editUser(@PathVariable Long idUser, @RequestBody UserDTO updatedUser  , HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.badRequest().body(ErrorMessage.NOT_ALLOWED);

        }

        if(!idUser.toString().equals(updatedUser.getId())){
            return ResponseEntity.badRequest().body(ErrorMessage.ILLEGAL_REQUEST);
        }
        try{
            this.userService.updateUser(idUser,updatedUser);
            return ResponseEntity.ok().body(SuccessMessage.DATA_WAS_UPDATED);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);

        }
    }

    /**
     *
     * @return
     */

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(SuccessMessage.SIGNED_OUT);
    }

    /**
     *
     * @param request
     * @return
     */

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
                                .body(SuccessMessage.TOKEN_REFRESHED);
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken,
                            "Refresh token is not in database!"));
        }

        return ResponseEntity.badRequest().body(ErrorMessage.TOKEN_EMPTY);
    }

}
