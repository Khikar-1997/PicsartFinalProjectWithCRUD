package com.company.controller.model.movie.personal.actor;

import com.company.persistance.model.movie.personal.Profession;

import java.time.LocalDateTime;
import java.util.Objects;

public class ActorResponseModel {
    private int id;
    LocalDateTime createdAt;
    private String name;
    private String surname;
    private int age;
    private String role;
    private Profession profession;

    public ActorResponseModel(int id, LocalDateTime createdAt, String name, String surname, int age, String role, Profession profession) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.role = role;
        this.profession = profession;
    }

    public ActorResponseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + name + "," + surname + "," + age + "," + profession + "," + role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorResponseModel that = (ActorResponseModel) o;
        return id == that.id && age == that.age && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(role, that.role) && profession == that.profession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, surname, age, role, profession);
    }

    @Override
    public String toString() {
        return id + "," + createdAt + "," + name + "," + surname + "," + age + "," + profession + "," + role;
    }
}
