package com.mundoasorrir.mundoasorrirbackend.Domain.User;
import jakarta.persistence.*;
@Entity
@Table(name = "system_role")
public class Role {
    @Id
    @Column(name = "sys_role_id")
    private Long sysRoleId;

    @Column(length = 20, unique = true)
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long sysRoleId, String name) {
        this.sysRoleId = sysRoleId;
        this.name = name;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }
    public static Role valueOf(final long id,final String role) {
        return new Role(id,role);
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

    @Override
    public String toString() {
        return this.name;
    }
}