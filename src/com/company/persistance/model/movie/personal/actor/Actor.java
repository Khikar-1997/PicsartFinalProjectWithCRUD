package com.company.persistance.model.movie.personal.actor;

import com.company.persistance.model.AbstractBaseEntity;
import com.company.persistance.model.movie.personal.Profession;

import java.time.LocalDateTime;
import java.util.Objects;

public class Actor extends AbstractBaseEntity {
    private String name;
    private String surname;
    private int age;
    private Profession profession;
    private String role;

    public Actor(int id, LocalDateTime createdAt, String name, String surname, int age, Profession profession, String role) {
        super(id, createdAt);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.profession = profession;
        this.role = role;
    }

    public Actor() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return age == actor.age && Objects.equals(name, actor.name) && Objects.equals(surname, actor.surname) && Objects.equals(profession, actor.profession) && Objects.equals(role, actor.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, profession, role);
    }

    @Override
    public String toString() {
        return super.getId() + "_" + super.getCreatedAt() + "_" + name + "_" + surname + "_" + age + "_" + profession + "_" + role;
    }
}
