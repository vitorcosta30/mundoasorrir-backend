package com.mundoasorrir.mundoasorrirbackend.DTO.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mundoasorrir.mundoasorrirbackend.DTO.Project.ProjectDTO;
import lombok.Getter;

public class UserDTO {
    @JsonProperty("email")
    private String email;
    @JsonProperty("id")

    private  String id;
    @JsonProperty("role")

    private  String role;
    @JsonProperty("username")

    private  String username;
    @JsonProperty("active")
    private Boolean active;


    @JsonProperty("currentProject")
    private ProjectDTO currentProject;


    public UserDTO(String id, String username, String email, String role, Boolean active, ProjectDTO currentProject) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.active = active;
        this.currentProject = currentProject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ProjectDTO getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(ProjectDTO currentProject) {
        this.currentProject = currentProject;
    }
}
