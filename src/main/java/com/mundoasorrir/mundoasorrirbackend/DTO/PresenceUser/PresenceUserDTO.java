package com.mundoasorrir.mundoasorrirbackend.DTO.PresenceUser;

import com.mundoasorrir.mundoasorrirbackend.DTO.User.UserDTO;

public class PresenceUserDTO {

    private UserDTO user;


    private String status;

    private String date;




    public PresenceUserDTO(UserDTO user, String status, String date) {
        this.user = user;
        this.status = status;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
