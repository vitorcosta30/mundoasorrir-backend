package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
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

}
