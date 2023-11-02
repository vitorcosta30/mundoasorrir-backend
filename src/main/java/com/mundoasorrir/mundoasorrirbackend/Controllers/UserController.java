package com.mundoasorrir.mundoasorrirbackend.Controllers;

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

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsServiceImpl userService;
    @GetMapping(value = "/getInfo", consumes = "multipart/form-data")
    public ResponseEntity<UserDetails> getUserInfo(@RequestParam("username") String username) throws IOException {
        //log.info("File name: {}", file.getOriginalFilename());

        userService.loadUserByUsername(username);

        return ResponseEntity.ok().body(userService.loadUserByUsername(username));
    }

}
