package com.mundoasorrir.mundoasorrirbackend.Domain.User;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "sys_user")
public class SystemUser {
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

    public SystemUser(String username,String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public SystemUser() {

    }


    public Long getUserId() {
        return userId;
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

    public Set<Role> getSystemRole() {
        Set<Role> roles = new HashSet<>(1);
        roles.add(this.systemRole);


        return roles;
    }
}
