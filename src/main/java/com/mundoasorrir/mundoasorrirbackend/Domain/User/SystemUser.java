package com.mundoasorrir.mundoasorrirbackend.Domain.User;

import com.mundoasorrir.mundoasorrirbackend.Domain.Attendance.Present;
import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Domain.Project.Project;
import com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup.UserGroup;
import com.mundoasorrir.mundoasorrirbackend.Domain.Vacation.Vacation;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "sys_user")
public class SystemUser {
    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    @Getter
    private String email;
    @Getter
    @Column(unique=true)
    private String username;

    @Getter
    private String password;
    @Getter
    @ManyToOne()
    private Role systemRole;

    @Getter
    @Column(nullable = false)
    private boolean isActive;

    @Getter
    @ManyToOne(optional = true)
    private Project currentProject;


    @ManyToMany(mappedBy = "enrolledUsers")
    private List<Event> event;

    @ManyToMany(mappedBy = "usersAllowed")
    private List<File> file;
    @OneToMany(mappedBy = "sharedBy")
    private List<File> sharedFiles;


    @ManyToMany(mappedBy = "groupUsers")
    private List<UserGroup> userGroup;
    @OneToMany(mappedBy = "createdBy")
    private List<UserGroup> createdGroups;

    @OneToMany(mappedBy = "user")
    private List<Vacation> vacation;


    @OneToMany(mappedBy = "user")
    private List<Present> presence;


    public SystemUser(String username,String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isActive = true;
        this.currentProject = null;
    }

    public SystemUser() {
        this.isActive = true;
    }

    public void deactivate(){
        if(this.isActive) {
            this.isActive = false;
        }
    }
    public void activate(){
        if(!this.isActive) {
            this.isActive = true;
        }
    }



    public void setUserId(Long identifier) {
        this.userId = identifier;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(Role role) {
        this.systemRole = role;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole(){
        return this.systemRole;
    }

    public Set<Role> getSystemRole() {
        Set<Role> roles = new HashSet<>(1);
        roles.add(this.systemRole);


        return roles;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setSystemRole(Role systemRole) {
        this.systemRole = systemRole;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
}
