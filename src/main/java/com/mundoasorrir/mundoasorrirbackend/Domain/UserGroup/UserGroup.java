package com.mundoasorrir.mundoasorrirbackend.Domain.UserGroup;

import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserGroup {
    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long groupId;

    private String groupName;

    @Getter
    private String groupDesignation;

    @ManyToOne
    private SystemUser createdBy;


    @Getter
    @ManyToMany
    private List<SystemUser> groupUsers;


    public UserGroup(String groupDesignation, SystemUser createdBy, String groupName) {
        this.groupName = groupName;
        this.groupDesignation = groupDesignation;
        this.createdBy = createdBy;
        this.groupUsers = new ArrayList<>();
    }

    public UserGroup() {
        this.groupUsers = new ArrayList<>();

    }

    public UserGroup(String groupName,SystemUser createdBy, String groupDesignation, List<SystemUser> groupUsers) {
        this.groupName = groupName;
        this.groupDesignation = groupDesignation;
        this.createdBy = createdBy;

        this.groupUsers = groupUsers;
    }

    public void addUser(SystemUser newUser){
        if(!isUserInGroup(newUser)){
            this.groupUsers.add(newUser);
        }
    }
    public void removeUser(SystemUser user){
        if(isUserInGroup(user)){
            this.groupUsers.remove(user);
        }
    }

    public boolean isUserInGroup(SystemUser user){
        for(int i = 0; i < this.groupUsers.size(); i++){
            if(user.equals(this.groupUsers.get(i))){
                return true;
            }
        }
        return false;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesignation() {
        return groupDesignation;
    }

    public void setGroupDesignation(String groupDesignation) {
        this.groupDesignation = groupDesignation;
    }

    public List<SystemUser> getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(List<SystemUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    public SystemUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SystemUser createdBy) {
        this.createdBy = createdBy;
    }
}
