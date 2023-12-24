package com.mundoasorrir.mundoasorrirbackend.Domain.Project;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Setter
    @Getter
    @Id
    @GeneratedValue
    private Long id;


    @Getter
    @Setter
    private String designation;

    @Setter
    @Getter
    private String location;

    @Getter
    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "currentProject")
    private List<SystemUser> users;

    public Project() {
    }

    public Project(String designation, String location) {
        this.designation = designation;
        this.location = location;
        this.isActive = true;
    }

    public List<SystemUser> getUsersOnVacationOnMonth(int month, int year){
        List<SystemUser> res = new ArrayList<>();
        for(int i = 0 ; i < this.users.size(); i++) {
            if (this.users.get(i).isUserOnVacationInMonth(month, year)) {
                res.add(this.users.get(i));
            }
        }
        return res;
    }

    public void deactivate(){
        this.isActive = false;
    }

    public void activate(){
        this.isActive = true;
    }


    public void setActive(boolean active) {
        isActive = active;
    }
}
