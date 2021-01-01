package com.company.controller.model.movie.soundtrack.personal.singer;

import com.company.persistance.model.movie.soundtrack.music.MusicGenre;

import java.time.LocalDateTime;
import java.util.Objects;

public class SingerResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String name;
    private String surname;
    private MusicGenre musicGenre;

    public SingerResponseModel(int id, String name, LocalDateTime createdAt, String surname, MusicGenre musicGenre) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.surname = surname;
        this.musicGenre = musicGenre;
    }

    public SingerResponseModel() {
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

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + name + "," + surname + "," + musicGenre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingerResponseModel that = (SingerResponseModel) o;
        return id == that.id && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && musicGenre == that.musicGenre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, surname, musicGenre);
    }

    @Override
    public String toString() {
        return "SingerResponseModel{" +
                "id=" + id +
                ", localDateTime=" + createdAt +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", musicGenre=" + musicGenre +
                '}';
    }
}
