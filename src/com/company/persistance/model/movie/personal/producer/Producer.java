package com.company.persistance.model.movie.personal.producer;

import com.company.persistance.model.AbstractBaseEntity;
import com.company.persistance.model.movie.personal.Profession;

import java.time.LocalDateTime;
import java.util.Objects;

public class Producer extends AbstractBaseEntity {
    private String name;
    private String surname;
    private int age;
    private Profession profession;

    public Producer(int id, LocalDateTime createdAt, String name, String surname, int age, Profession profession) {
        super(id, createdAt);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.profession = profession;
    }

    public Producer() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Producer producer = (Producer) o;
        return age == producer.age &&
                Objects.equals(name, producer.name) &&
                Objects.equals(surname, producer.surname) &&
                profession == producer.profession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, age, profession);
    }

    @Override
    public String toString() {
        return super.getId() + "_" + super.getCreatedAt() + "_" + name + "_" + surname + "_" + age + "_" + profession;
    }
}
