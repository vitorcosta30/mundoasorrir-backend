package com.mundoasorrir.mundoasorrirbackend.Domain.User;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "sys_user")
public class SystemUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    private String email;
    @Column(unique=true)
    private String username;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
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
