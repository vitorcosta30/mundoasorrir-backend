package com.mundoasorrir.mundoasorrirbackend.DTO.User;

public class UserDTO {
    private final String email;
    private  String id;
    private  String role;
    private  String username;




    public UserDTO(String id, String username, String email, String role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
