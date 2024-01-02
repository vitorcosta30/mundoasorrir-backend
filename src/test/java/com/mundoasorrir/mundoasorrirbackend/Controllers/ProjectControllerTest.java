package com.mundoasorrir.mundoasorrirbackend.Controllers;

import com.mundoasorrir.mundoasorrirbackend.Auth.AuthUtils;
import com.mundoasorrir.mundoasorrirbackend.Auth.JwtUtils;
import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import com.mundoasorrir.mundoasorrirbackend.Repositories.ProjectRepository;
import com.mundoasorrir.mundoasorrirbackend.Services.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;

class ProjectControllerTest {
    @InjectMocks
    private ProjectService projectService;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    JwtUtils jwtUtils;
    @Mock
     AuthUtils authUtils;
    @InjectMocks
    ProjectController projectController;

    @Test
    void createProject() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(authUtils.isLoggedIn((HttpServletRequest)notNull())).thenReturn(false);
        projectService.save(new Project("Test project","test location"));
    }
}