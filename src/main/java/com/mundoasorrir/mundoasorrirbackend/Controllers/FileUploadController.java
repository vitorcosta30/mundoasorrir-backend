package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.DTO.File.FileDTO;
import com.mundoasorrir.mundoasorrirbackend.DTO.File.FileMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseFile;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Services.FileUploadService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserService;
import com.mundoasorrir.mundoasorrirbackend.Services.UserGroupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {
    @Autowired

    private final FileUploadService fileUploadService;

    private final UserService userService;

    private final UserGroupService userGroupService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/upload")
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("users") List<String> users,@RequestParam("groups") List<String> groups, HttpServletRequest request) {
        List<Long> groupsConverted = new ArrayList<>();
        for(int i = 0 ; i < groups.size(); i++){
            groupsConverted.add(Long.valueOf(groups.get(i)));
        }

        String message = "";
        List<SystemUser> usersAllowed = new ArrayList<>();
        usersAllowed.addAll(this.getUsersFromGroup(groupsConverted));
        usersAllowed.addAll(this.getUsersFromUsername(users));
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);


        try {
            fileUploadService.store(file, usersAllowed, user);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }



    private List<SystemUser> getUsersFromGroup(List<Long> groups){
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0; i < groups.size(); i++){
            UserGroup group = this.userGroupService.findByGroupId(groups.get(i));
            res.addAll(group.getGroupUsers());
        }
        return res;
    }
    private List<SystemUser> getUsersFromUsername(List<String> users){
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            SystemUser user = this.userService.findUserByUsername(users.get(i));
            res.add(user);
        }
        return res;
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getListFiles(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));


        List<FileDTO> files = FileMapper.toDTO(fileUploadService.getAllFiles(username).toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id, HttpServletRequest request ) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromCookies(request));
        SystemUser user = this.userService.findUserByUsername(username);
        if(fileUploadService.getFile(id).isUserAllowed(user)){
            File fileDB = fileUploadService.getFile(id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                    .body(fileDB.getData());
        }else{
            String message = "Not allowed";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(message));


        }



    }

}
