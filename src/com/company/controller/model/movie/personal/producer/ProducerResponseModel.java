package com.company.controller.model.movie.personal.producer;

import com.company.persistance.model.movie.personal.Profession;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProducerResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String name;
    private String surname;
    private int age;
    private Profession profession;

    public ProducerResponseModel(int id, LocalDateTime createdAt, String name, String surname, int age, Profession profession) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.profession = profession;
    }

    public ProducerResponseModel() {
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
        return id + "," + createdAt + "," + name + "," + surname + "," + age + "," + profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducerResponseModel that = (ProducerResponseModel) o;
        return id == that.id && age == that.age && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && profession == that.profession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, surname, age, profession);
    }

    @Override
    public String toString() {
        return "ProducerResponseModel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", profession=" + profession +
                '}';
    }
}