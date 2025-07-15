package com.kitaplik.backend.dto;

public class UserResponse {
    private Long id;
    private String username;

    // Constructor
    public UserResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getter ve Setter'lar
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}