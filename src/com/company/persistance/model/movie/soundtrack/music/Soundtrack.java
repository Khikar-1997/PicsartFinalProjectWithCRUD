package com.company.persistance.model.movie.soundtrack.music;

import com.company.persistance.model.AbstractBaseEntity;
import com.company.persistance.model.movie.soundtrack.personal.composer.Composer;
import com.company.persistance.model.movie.soundtrack.song.Song;

import java.time.LocalDateTime;
import java.util.Objects;

public class Soundtrack extends AbstractBaseEntity {

    private String duration;
    private Song song;
    private Composer composer;

    public Soundtrack(int id, LocalDateTime createdAt, String duration, Song song, Composer composer) {
        super(id, createdAt);
        this.duration = duration;
        this.song = song;
        this.composer = composer;
    }

    public Soundtrack() {
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Composer getComposer() {
        return composer;
    }

    public void setComposer(Composer composer) {
        this.composer = composer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Soundtrack that = (Soundtrack) o;
        return Objects.equals(duration, that.duration) &&
                Objects.equals(song, that.song) &&
                Objects.equals(composer, that.composer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration, song, composer);
    }

    @Override
    public String toString() {
        return super.getId() + "`" + super.getCreatedAt() + "`" + duration + "`" + song + "`" + composer;
    }
}
