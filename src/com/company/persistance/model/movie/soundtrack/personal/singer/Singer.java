package com.company.persistance.model.movie.soundtrack.personal.singer;

import com.company.persistance.model.AbstractBaseEntity;
import com.company.persistance.model.movie.soundtrack.music.MusicGenre;

import java.time.LocalDateTime;
import java.util.Objects;

public class Singer extends AbstractBaseEntity {
    private String name;
    private String surname;
    private MusicGenre musicGenre;

    public Singer(int id, LocalDateTime createdAt, String name, String surname, MusicGenre musicGenre) {
        super(id, createdAt);
        this.name = name;
        this.surname = surname;
        this.musicGenre = musicGenre;
    }

    public Singer() {
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
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Singer singer = (Singer) o;
        return Objects.equals(name, singer.name) && Objects.equals(surname, singer.surname) && musicGenre == singer.musicGenre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, musicGenre);
    }

    @Override
    public String toString() {
        return super.getId() + "|" + super.getCreatedAt()
                 + "|" + name + "|"+ surname + "|" + musicGenre;
    }
}
