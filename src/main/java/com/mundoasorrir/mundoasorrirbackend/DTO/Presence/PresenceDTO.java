package com.mundoasorrir.mundoasorrirbackend.DTO.Presence;

import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;

public class PresenceDTO {

    private UserDTO user;


    private String status;


    public PresenceDTO(UserDTO user, String status) {
        this.user = user;
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
