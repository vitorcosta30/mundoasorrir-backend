package com.mundoasorrir.mundoasorrirbackend.DTO.Project;

public class ProjectDTO {

    private Long id;

    private String designation;

    private String location;

    public ProjectDTO(Long id, String designation, String location) {
        this.id = id;
        this.designation = designation;
        this.location = location;
    }

    public ProjectDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
