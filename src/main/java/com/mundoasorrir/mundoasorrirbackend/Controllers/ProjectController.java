package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.Response.MessageResponse;
import com.mundoasorrir.mundoasorrirbackend.DTO.Project.ProjectMapper;
import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserMapper;
import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Message.ErrorMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.ResponseMessage;
import com.mundoasorrir.mundoasorrirbackend.Message.SuccessMessage;
import com.mundoasorrir.mundoasorrirbackend.Services.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "${mundoasorrir.app.frontend}", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);


    @Autowired
    private ProjectService projectService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private final AuthUtils authUtils;

    /**
     *
     * @param designation
     * @param location
     * @param request
     * @return
     */
    @PostMapping("/createProject")
    public ResponseEntity<?> createProject(@RequestParam("designation") String designation, @RequestParam("location")String location, HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            this.projectService.save(new Project(designation,location));
            return ResponseEntity.ok().body(SuccessMessage.PROJECT_CREATED);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @return
     */
    @GetMapping("/getProjects")
    public ResponseEntity<?> getProjects(){
        try{
            return ResponseEntity.ok().body(ProjectMapper.toDTO(this.projectService.getActiveProjects()));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param request
     * @return
     */

    @GetMapping("/getAllProjects")
    public ResponseEntity<?> getAllProjects( HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }

        try{
            return ResponseEntity.ok().body(ProjectMapper.toDTO(this.projectService.findAll()));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param id
     * @param year
     * @param month
     * @param request
     * @return
     */
    @GetMapping(value="/getUsersOnVacation/{id}/{year}/{month}")
    public ResponseEntity<?> getUsersOnVacation(@PathVariable Long id, @PathVariable int year, @PathVariable int month , HttpServletRequest request ) {
        if(!this.authUtils.mediumPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            List<SystemUser> users = this.projectService.getUsersOnVacationInMonth(id,month,year);
            return ResponseEntity.ok().body(UserMapper.toDTO(users));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */

    @PostMapping(value="/activateProject/{id}")
    public ResponseEntity<?> activateAccount(@PathVariable Long id, HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            this.projectService.activateProject(id);
            return ResponseEntity.ok(SuccessMessage.PROJECT_ACTIVATED);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

    /**
     *
     * @param id
     * @param request
     * @return
     */

    @PostMapping(value="/deactivateProject/{id}")
    public ResponseEntity<?> deactivateAccount(@PathVariable Long id, HttpServletRequest request){
        if(!this.authUtils.highPermissions(request)){
            return ResponseEntity.status(401).body(ErrorMessage.NOT_ALLOWED);
        }
        try{
            this.projectService.deactivateProject(id);
            return ResponseEntity.ok(SuccessMessage.PROJECT_DEACTIVATED);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(ErrorMessage.ERROR);
        }
    }

}



