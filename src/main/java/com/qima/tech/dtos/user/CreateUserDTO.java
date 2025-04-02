package com.qima.tech.dtos.user;


public class CreateUserDTO {
    private String username;
    private String email;
    private String password;

    public CreateUserDTO() {}

    public CreateUserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

