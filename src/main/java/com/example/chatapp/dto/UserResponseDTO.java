package com.example.chatapp.dto;

public class UserResponseDTO {
    private String username;
    private String email;

    public UserResponseDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
