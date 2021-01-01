package com.company.persistance.model.movie.soundtrack.personal.composer;

import com.company.persistance.model.AbstractBaseEntity;
import com.company.persistance.model.movie.soundtrack.music.MelodyGenre;

import java.time.LocalDateTime;
import java.util.Objects;

public class Composer extends AbstractBaseEntity {

    private String name;
    private String surname;
    private MelodyGenre melodyGenre;

    public Composer(int id, LocalDateTime createdAt, String name, String surname, MelodyGenre melodyGenre) {
        super(id, createdAt);
        this.name = name;
        this.surname = surname;
        this.melodyGenre = melodyGenre;
    }

    public Composer() {
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

    public MelodyGenre getMelodyGenre() {
        return melodyGenre;
    }

    public void setMelodyGenre(MelodyGenre melodyGenre) {
        this.melodyGenre = melodyGenre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Composer composer = (Composer) o;
        return Objects.equals(name, composer.name) && Objects.equals(surname, composer.surname) && melodyGenre == composer.melodyGenre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, melodyGenre);
    }


    @Override
    public String toString() {
        return super.getId() + "|" + super.getCreatedAt() + "|" + name + "|" + surname + "|" + melodyGenre;
    }
}
