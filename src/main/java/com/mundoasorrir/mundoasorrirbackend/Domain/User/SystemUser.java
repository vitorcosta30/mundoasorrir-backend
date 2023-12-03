package com.mundoasorrir.mundoasorrirbackend.Domain.User;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.Event;
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

    @ManyToOne(cascade = CascadeType.ALL)
    private Role systemRole;

    @Getter
    @Column(nullable = false)
    private boolean isActive;

    public SystemUser(String username,String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isActive = true;
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
}
