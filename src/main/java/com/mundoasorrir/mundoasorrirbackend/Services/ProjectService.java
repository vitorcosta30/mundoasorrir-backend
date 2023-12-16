package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {


    @Autowired
    private final ProjectRepository projectRepository;


    public Project save(Project user){
        return this.projectRepository.save(user);
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Project getProjectById(Long id){
        return this.projectRepository.findProjectById(id);
    }

    public List<Project> getActiveProjects(){
        return this.projectRepository.findActiveProject();
    }

    public List<SystemUser> getUsersOnVacationInMonth(Long id, int month, int year){
        return getProjectById(id).getUsersOnVacationOnMonth(month, year);
    }

    public Project activateProject(Long id){
        Project project = this.getProjectById(id);
        project.activate();
        return this.save(project);
    }
    public Project deactivateProject(Long id){
        Project project = this.getProjectById(id);
        project.deactivate();
        return this.save(project);
    }


}
