package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.Project.ProjectMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Services.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private final AuthUtils authUtils;

    @PostMapping("/createProject")
    public ResponseEntity<?> createProject(@RequestParam("designation") String designation, @RequestParam("location")String location, HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(new MessageResponse("Not allowed"));
        }
        try{
            this.projectService.save(new Project(designation,location));
            return ResponseEntity.ok().body(new MessageResponse("Project created successfully!!"));

        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: There has been an error creating the project!!"));
        }
    }
    @GetMapping("/getProjects")
    public ResponseEntity<?> getProjects(){
        try{
            return ResponseEntity.ok().body(ProjectMapper.toDTO(this.projectService.findAll()));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: There has been an error creating the project!!"));
        }
    }
}



