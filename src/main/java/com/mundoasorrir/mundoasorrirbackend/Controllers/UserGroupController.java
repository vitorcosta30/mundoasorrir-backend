package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.DTO.UserGroup.UserGroupDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.UserGroup.UserGroupMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Message.ErrorMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.SuccessMessage;
import com.mundoasorrir.mundoasorrirbackend.Services.UserGroupService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/userGroup")
@RequiredArgsConstructor
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);


    private final UserService userService;

    @Autowired
    private AuthUtils authUtils;

    private final UserGroupService userGroupService;
    @Autowired
    JwtUtils jwtUtils;

    /**
     *
     * @param groupName
     * @param groupDesignation
     * @param request
     * @param users
     * @return
     */
    @PostMapping("/createGroup")
    public ResponseEntity<?> createGroup(@RequestParam("groupName") String groupName, @RequestParam("groupDesignation") String groupDesignation, HttpServletRequest request, @RequestParam(value = "users", required = false) List<String> users) {
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        if (userGroupService.existsByGroupName(groupName)) {
            return ResponseEntity.badRequest().body(ErrorMessage.GROUPNAME_IN_USE);
        }


        List<SystemUser> usersInGroup = new ArrayList<>();
        if(users != null){
            usersInGroup.addAll(this.getUsersFromUsername(users));
        }


        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));

        String message = "";
        usersInGroup = usersInGroup.stream().distinct().toList();
        List<SystemUser> usersInGroupMut = new ArrayList<>(usersInGroup);

        UserGroup userGroup = new UserGroup(groupName,this.userService.findUserByUsername(username),groupDesignation,usersInGroupMut);

        try {
            userGroupService.save(userGroup);
            message = "Group created succesfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/getGroupsSimple")
    public ResponseEntity<?> getGroups( HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        List<UserGroupDTO> res = UserGroupMapper.toDTO(userGroupService.findUserGroups(username));
        return ResponseEntity.ok().body(res);
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */


    @GetMapping("/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id, HttpServletRequest request){
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try {
            String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
            if (isUserInGroup(username, id)) {
                return ResponseEntity.ok().body(UserGroupMapper.toDTO(this.userGroupService.getByGroupId(id)));
            } else {
                return ResponseEntity.badRequest().body(ErrorMessage.NOT_ALLOWED);

            }
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }

    }

    /**
     *
     * @param groupId
     * @param request
     * @return
     */


    @PostMapping("/isUserCreator")
    public ResponseEntity<Boolean> isUserCreator(@RequestParam("groupId") Long groupId,HttpServletRequest request ){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);
        return ResponseEntity.ok().body(this.userGroupService.isUserCreator(user,groupId));
    }

    /**
     *
     * @param groupId
     * @param request
     * @return
     */

    @PostMapping("/leaveGroup")
    public ResponseEntity<?> leaveGroup(@RequestParam("groupId") Long groupId,HttpServletRequest request ){
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);
        try{
            this.userGroupService.removeUser(user,groupId);
            return ResponseEntity.ok().body(SuccessMessage.LEFT_GROUP);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param groupId
     * @param username
     * @param request
     * @return
     */

    @PostMapping("/removeUser")
    public ResponseEntity<?> removeUser(@RequestParam("groupId") Long groupId,@RequestParam("username")String username,HttpServletRequest request ){
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        SystemUser user = this.userService.findUserByUsername(username);
        try{
            this.userGroupService.removeUser(user,groupId);
            logger.info("User " + username + " was removed from group " + groupId);

            return ResponseEntity.ok().body(SuccessMessage.USER_REMOVED);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param groupId
     * @param username
     * @param request
     * @return
     */


    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestParam("groupId") Long groupId,@RequestParam("username")String username, HttpServletRequest request ){
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        SystemUser user = this.userService.findUserByUsername(username);
        try{
            this.userGroupService.addUser(user,groupId);
            logger.info("User " + username + " was added to group " + groupId);
            return ResponseEntity.ok().body(SuccessMessage.USER_ADDED);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param groupId
     * @param request
     * @return
     */

    @PostMapping("/getUsersInGroup")
    public ResponseEntity<?> getUsersInGroup(@RequestParam("groupId") Long groupId, HttpServletRequest request ){
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            return ResponseEntity.ok().body(UserMapper.toDTO(this.userGroupService.getUsersInGroup(groupId)));

        }catch(Exception e){
            return ResponseEntity.ok().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param groupId
     * @param request
     * @return
     */

    @PostMapping("/getUsersNotInGroup")
    public ResponseEntity<?> getUsersNotInGroup(@RequestParam("groupId") Long groupId,HttpServletRequest request ){
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        List<SystemUser> users = this.userService.findAll();
        users.removeAll(this.userGroupService.getUsersInGroup(groupId));

        try{
            return ResponseEntity.ok().body(UserMapper.toDTO(users));

        }catch(Exception e){
            return ResponseEntity.ok().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */



    @GetMapping(value = "/getGroupMembers/{id}")
    public ResponseEntity<?> getGroupMembers(@PathVariable Long id, HttpServletRequest request) {
        if(!this.authUtils.lowPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        List<UserDTO> res = UserMapper.toDTO(userGroupService.findByGroupId(id).getGroupUsers());
        return ResponseEntity.ok().body(res);
    }

    /**
     *
     * @param users
     * @return
     */

    private List<SystemUser> getUsersFromUsername(List<String> users){
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            SystemUser user = this.userService.findUserByUsername(users.get(i));
            res.add(user);
        }
        return res;
    }

    /**
     *
     * @param username
     * @param groupId
     * @return
     */

    private Boolean isUserInGroup(String username, Long groupId){
        SystemUser user = this.userService.findUserByUsername(username);
        return this.userGroupService.isUserInGroup(user,groupId);
    }
}
