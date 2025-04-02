package com.qima.tech.dtos.user;

public class UpdateUserDTO {
    private String email;
    private String password;

    public UpdateUserDTO() {}

    public UpdateUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
