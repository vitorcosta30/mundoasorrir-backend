package com.mundoasorrir.mundoasorrirbackend.DTO.ChangePassword;

public class ChangePasswordDTO {
    private String username;

    private String newPassword;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
