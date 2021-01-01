package com.company.controller.model.movie.personal.actor;

import com.company.persistance.model.movie.personal.Profession;

import java.util.Objects;

public class ActorRequestModel {
    private String name;
    private String surname;
    private int age;
    private String role;
    private Profession profession;

    public ActorRequestModel(String name, String surname, int age,String role, Profession profession) {
        this.name = name;
        this.surname = surname;
        setAge(age);
        this.role = role;
        setProfession(profession);
    }

    public ActorRequestModel() {
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
        if (age > 0 && age <= 100) {
            this.age = age;
        } else {
            throw new RuntimeException("Actor age is not valid");
        }
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
        if (profession.equals(Profession.ACTOR)) {
            this.profession = profession;
        } else {
            throw new RuntimeException("Profession is not valid");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActorRequestModel)) return false;
        ActorRequestModel that = (ActorRequestModel) o;
        return getAge() == that.getAge() && Objects.equals(getName(), that.getName()) && Objects.equals(getSurname(), that.getSurname()) && Objects.equals(getRole(), that.getRole()) && getProfession() == that.getProfession();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getAge(), getRole(), getProfession());
    }

    @Override
    public String toString() {
        return "ActorRequestModel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", profession=" + profession +
                '}';
    }
}
