package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.BaseRoles;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.Role;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Repositories.UserRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.FileUploadService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserDetailsImpl;
import com.mundoasorrir.mundoasorrirbackend.Services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
    private final UserDetailsServiceImpl userService;
    @GetMapping(value = "/getInfo/{username}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable String username) {
        //log.info("File name: {}", file.getOriginalFilename());
        UserDTO user = UserMapper.toDTO(this.userService.findUserByUsername(username));


        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/getRoles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(Arrays.asList(BaseRoles.systemRoles()));
    }


    @GetMapping(value = "/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> res = UserMapper.toDTO(userService.findAll());
        return ResponseEntity.ok().body(res);
    }

}
