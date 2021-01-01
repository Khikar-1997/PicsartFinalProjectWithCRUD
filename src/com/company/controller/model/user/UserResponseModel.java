package com.company.controller.model.user;

import com.company.persistance.model.user.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String name;
    private String surname;
    private Role role;
    private String email;
    private String username;
    private String password;

    public UserResponseModel(int id, LocalDateTime createdAt, String name, String surname, Role role, String email, String username, String password) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public UserResponseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + name + "," + surname + "," + role + "," + email + "," + username + "," + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseModel that = (UserResponseModel) o;
        return id == that.id && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && role == that.role && Objects.equals(email, that.email) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, surname, role, email, username, password);
    }

    @Override
    public String toString() {
        return "UserResponseModel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

