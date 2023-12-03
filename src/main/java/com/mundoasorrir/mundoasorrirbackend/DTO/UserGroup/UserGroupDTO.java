package com.mundoasorrir.mundoasorrirbackend.DTO.UserGroup;

public class UserGroupDTO {
    public String getGroupDesignation() {
        return groupDesignation;
    }

    public void setGroupDesignation(String groupDesignation) {
        this.groupDesignation = groupDesignation;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private String groupDesignation;

    private String groupName;
    private String id;


    public UserGroupDTO(String id, String groupDesignation, String groupName) {
        this.id = id;
        this.groupDesignation = groupDesignation;
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
