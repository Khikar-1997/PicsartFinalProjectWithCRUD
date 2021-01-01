package com.company.controller.model.movie.soundtrack.personal.composer;

import com.company.persistance.model.movie.soundtrack.music.MelodyGenre;

import java.time.LocalDateTime;
import java.util.Objects;

public class ComposerResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String name;
    private String surname;
    private MelodyGenre melodyGenre;

    public ComposerResponseModel(int id, String name, String surname, MelodyGenre melodyGenre, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.surname = surname;
        this.melodyGenre = melodyGenre;
    }

    public ComposerResponseModel() {
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

    public MelodyGenre getMelodyGenre() {
        return melodyGenre;
    }

    public void setMelodyGenre(MelodyGenre melodyGenre) {
        this.melodyGenre = melodyGenre;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + name + "," + surname + "," + melodyGenre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComposerResponseModel that = (ComposerResponseModel) o;
        return id == that.id && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && melodyGenre == that.melodyGenre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, surname, melodyGenre);
    }

    @Override
    public String toString() {
        return id + "|" + createdAt + "|" + name + "|" + surname + "|" + melodyGenre;
    }
}
