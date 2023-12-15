package com.example.studentproblembook;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры для username и password

    public void set_username(String username) {
        this.username = username;
    }

    public String get_username() {
        return this.username;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public String get_password() {
        return this.password;
    }
}
