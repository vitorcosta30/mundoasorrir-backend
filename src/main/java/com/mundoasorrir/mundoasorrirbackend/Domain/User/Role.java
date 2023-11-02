package com.mundoasorrir.mundoasorrirbackend.Domain.User;
import jakarta.persistence.*;
@Entity
@Table(name = "system_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sysRoleId;

    @Column(length = 20)
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }
    public static Role valueOf(final String role) {
        return new Role(role);
    }

    public void setSysRoleId(Long id) {
        this.sysRoleId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}