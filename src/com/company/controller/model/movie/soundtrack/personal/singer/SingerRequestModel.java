package com.company.controller.model.movie.soundtrack.personal.singer;

import com.company.persistance.model.movie.soundtrack.music.MusicGenre;

import java.util.Objects;

public class SingerRequestModel {
    private String name;
    private String surname;
    private MusicGenre musicGenre;

    public SingerRequestModel(String name, String surname, MusicGenre musicGenre) {
        this.name = name;
        this.surname = surname;
        this.musicGenre = musicGenre;
    }

    public SingerRequestModel() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingerRequestModel)) return false;
        SingerRequestModel that = (SingerRequestModel) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getSurname(), that.getSurname()) && getMusicGenre() == that.getMusicGenre();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getMusicGenre());
    }

    @Override
    public String toString() {
        return "SingerRequestModel{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", musicGenre=" + musicGenre +
                '}';
    }
}
