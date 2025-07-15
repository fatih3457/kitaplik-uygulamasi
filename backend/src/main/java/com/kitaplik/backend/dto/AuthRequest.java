package com.kitaplik.backend.dto;

// Lombok kullanmadığımız için getter/setter elle ekliyoruz
public class AuthRequest {
    private String username;
    private String password;

    // Getter ve Setter'lar
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}