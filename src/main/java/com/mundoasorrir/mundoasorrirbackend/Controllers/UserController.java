package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.PresenceUser.PresenceUserMapper;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;
import com.mundoasorrir.mundoasorrirbackend.Message.ErrorMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.SuccessMessage;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Slf4j
@RestController
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private AuthUtils authUtils;

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     *
     * @param username
     * @param request
     * @return
     */

    @GetMapping(value = "/getInfo/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username, HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try {

            UserDTO user = UserMapper.toDTO(this.userService.findUserByUsername(username));
            return ResponseEntity.ok().body(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @return
     */

    @GetMapping(value = "/getRoles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(Arrays.asList(BaseRoles.systemRoles()));
    }

    /**
     *
     * @param request
     * @return
     */


    @GetMapping(value = "/getUsers")
    public ResponseEntity<?> getUsers(HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        List<UserDTO> res = UserMapper.toDTO(userService.findAll());
        return ResponseEntity.ok().body(res);
    }

    /**
     *
     * @param idUser
     * @param updatedUser
     * @param request
     * @return
     */



    @RequestMapping(value = "/updateUser/{idUser}",method = {RequestMethod.OPTIONS,RequestMethod.PUT}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editUser(@PathVariable Long idUser, @RequestBody UserDTO updatedUser , HttpServletRequest request ){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        if(!idUser.toString().equals(updatedUser.getId())){
            return ResponseEntity.badRequest().body(ErrorMessage.ILLEGAL_REQUEST);
        }
        if (userService.existsByUsername(idUser,updatedUser)) {
            return ResponseEntity.badRequest().body(ErrorMessage.USERNAME_IN_USE);
        }

        if (userService.existsByEmail(idUser,updatedUser)) {
            return ResponseEntity.badRequest().body(ErrorMessage.EMAIL_IN_USE);
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
     * @param username
     * @param request
     * @return
     */




    @PostMapping(value="/deactivateAccount")
    public ResponseEntity<?> deactivateAccount(@RequestParam("username") String username, HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            this.userService.deactivateUser(username);
            return ResponseEntity.ok(SuccessMessage.ACCOUNT_DEACTIVATED);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ACCOUNT_DEACTIVATION_FAILED);
        }
    }

    @PostMapping(value="/activateAccount")
    public ResponseEntity<?> activateAccount(@RequestParam("username") String username, HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            this.userService.activateUser(username);
            return ResponseEntity.ok(SuccessMessage.ACCOUNT_ACTIVATED);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ACCOUNT_ACTIVATION_FAILED);
        }
    }

    /**
     *
     * @param username
     * @param year
     * @param month
     * @param request
     * @return
     */
    @GetMapping(value="/getPresences/{username}/{year}/{month}")
    public ResponseEntity<?> getPresencesMoth(@PathVariable String username, @PathVariable int year , @PathVariable int month, HttpServletRequest request ) {
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            List<Present> presences = this.userService.getPresencesInMonth(username,month,year);
            return ResponseEntity.ok().body(PresenceUserMapper.toDTO(presences));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }

    }

    /**
     *
     * @param username
     * @param year
     * @param request
     * @return
     */

    @GetMapping(value="/getPresences/{username}/{year}")
    public ResponseEntity<?> getPresencesYear(@PathVariable String username, @PathVariable int year , HttpServletRequest request ) {
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            List<Present> presences = this.userService.getPresencesInYear(username,year);
            return ResponseEntity.ok().body(PresenceUserMapper.toDTO(presences));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }

    }


}
