package com.company.persistance.model.user;

import com.company.persistance.model.AbstractBaseEntity;


import java.time.LocalDateTime;
import java.util.Objects;

public class User extends AbstractBaseEntity {
    private String name;
    private String surname;
    private Role role;
    private String emile;
    private String username;
    private String password;

    public User(int id, LocalDateTime createdAt, String name, String surname, Role role, String emile, String username, String password) {
        super(id, createdAt);
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.emile = emile;
        this.username = username;
        this.password = password;
    }

    public User() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmile() {
        return emile;
    }

    public void setEmile(String emile) {
        this.emile = emile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && role == user.role && Objects.equals(emile, user.emile) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, role, emile, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", emile='" + emile + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
