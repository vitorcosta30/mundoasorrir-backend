package com.mundoasorrir.mundoasorrirbackend.DTO.Project;

public class ProjectDTO {

    private Long id;

    private String designation;

    private String location;

    private Boolean active;


    public ProjectDTO(Long id, String designation, String location, boolean active) {
        this.id = id;
        this.designation = designation;
        this.location = location;
        this.active = active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
