package com.company.controller.model.movie.soundtrack.personal.composer;

import com.company.persistance.model.movie.soundtrack.music.MelodyGenre;

import java.util.Objects;

public class ComposerRequestModel {
    private String name;
    private String surname;
    private MelodyGenre melodyGenre;

    public ComposerRequestModel(String name, String surname, MelodyGenre melodyGenre) {
        this.name = name;
        this.surname = surname;
        this.melodyGenre = melodyGenre;
    }

    public ComposerRequestModel() {
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
        if (!(o instanceof ComposerRequestModel)) return false;
        ComposerRequestModel that = (ComposerRequestModel) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getSurname(), that.getSurname()) && getMelodyGenre() == that.getMelodyGenre();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getMelodyGenre());
    }

    @Override
    public String toString() {
        return "ComposerRequestModel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", melodyGenre=" + melodyGenre +
                '}';
    }
}
