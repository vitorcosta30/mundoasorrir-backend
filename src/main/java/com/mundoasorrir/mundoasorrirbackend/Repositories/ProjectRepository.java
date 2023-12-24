package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository  extends JpaRepository<Project, Long> {

    Project findProjectById(Long id);

    @Query("select p from Project p where p.isActive = true")
    List<Project> findActiveProject();
}
