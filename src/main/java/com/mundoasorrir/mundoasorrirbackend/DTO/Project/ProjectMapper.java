package com.mundoasorrir.mundoasorrirbackend.DTO.Project;

import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project){
        return new ProjectDTO(project.getId(),project.getDesignation(),project.getLocation(),project.isActive());
    }

    public static List<ProjectDTO> toDTO(List<Project> projects){
        List<ProjectDTO> res = new ArrayList<>();
        for(int i = 0; i < projects.size(); i++) {
            res.add(toDTO(projects.get(i)));
        }
        return res;
    }
}
