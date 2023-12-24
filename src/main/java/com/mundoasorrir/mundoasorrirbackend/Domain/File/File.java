package com.mundoasorrir.mundoasorrirbackend.Domain.File;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @ManyToMany
    private List<SystemUser> usersAllowed;

    @ManyToOne(optional = true)
    private SystemUser sharedBy;



    //private Long owner;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] data;


    public File() {}

    public File(String name, String type, byte[] data, List<SystemUser> usersAllowed) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.usersAllowed = usersAllowed;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public boolean isUserAllowed(SystemUser user){
        return this.usersAllowed.contains(user);
    }

    public boolean isShared(SystemUser user){return this.sharedBy != null && this.sharedBy.equals(user);}

    public void setData(byte[] data) {
        this.data = data;
    }

    public SystemUser getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(SystemUser sharedBy) {
        this.sharedBy = sharedBy;
        this.usersAllowed.add(sharedBy);
    }
    public void removeSharedBy(){
        this.sharedBy = null;
    }

    public List<SystemUser> getUsersAllowed() {
        return usersAllowed;
    }
}
