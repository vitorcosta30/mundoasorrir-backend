package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.DTO.UserGroup.UserGroupDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.UserGroup.UserGroupMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Services.UserDetailsServiceImpl;
import com.mundoasorrir.mundoasorrirbackend.Services.UserGroupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserDetailsServiceImpl userService;

    private final UserGroupService userGroupService;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/createGroup")
    public ResponseEntity<?> createGroup(@RequestParam("groupName") String groupName, @RequestParam("groupDesignation") String groupDesignation, HttpServletRequest request, @RequestParam("users") List<String> users) {
        List<SystemUser> usersInGroup = new ArrayList<>(this.getUsersFromUsername(users));
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        usersInGroup.add(this.userService.findUserByUsername(username));

        UserGroup userGroup = new UserGroup(groupName,this.userService.findUserByUsername(username),groupDesignation,usersInGroup);
        String message = "";

        try {
            userGroupService.save(userGroup);
            message = "Group created succesfully";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Error while creating group";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/getGroupsSimple")
    public ResponseEntity<List<UserGroupDTO>> getGroups( HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        List<UserGroupDTO> res = UserGroupMapper.toDTO(userGroupService.findUserGroups(username));
        return ResponseEntity.ok().body(res);
    }


    @PostMapping("/isUserCreator")
    public ResponseEntity<Boolean> isUserCreator(@RequestParam("groupId") Long groupId,HttpServletRequest request ){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);
        return ResponseEntity.ok().body(this.userGroupService.isUserCreator(user,groupId));
    }

    @PostMapping("/leaveGroup")
    public ResponseEntity<?> leaveGroup(@RequestParam("groupId") Long groupId,HttpServletRequest request ){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);
        try{
            this.userGroupService.removeUser(user,groupId);
            return ResponseEntity.ok().body(new ResponseMessage("Left group successfully"));

        }catch(Exception e){
            return ResponseEntity.ok().body(new ResponseMessage("There was an error leaving the group"));
        }
    }

    @PostMapping("/getUsersInGroup")
    public ResponseEntity<?> getUsersInGroup(@RequestParam("groupId") Long groupId,HttpServletRequest request ){
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);
        try{
            this.userGroupService.removeUser(user,groupId);
            return ResponseEntity.ok().body(this.userGroupService.getUsersInGroup(groupId));

        }catch(Exception e){
            return ResponseEntity.ok().body(new ResponseMessage("There was an error getting the users!"));
        }
    }



    @GetMapping(value = "/getGroupMembers/{id}")
    public ResponseEntity<List<UserDTO>> getGroupMembers(@PathVariable Long id) {
        List<UserDTO> res = UserMapper.toDTO(userGroupService.findByGroupId(id).getGroupUsers());
        return ResponseEntity.ok().body(res);
    }

    private List<SystemUser> getUsersFromUsername(List<String> users){
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            SystemUser user = this.userService.findUserByUsername(users.get(i));
            res.add(user);
        }
        return res;
    }
}
