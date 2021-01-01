package com.company.persistance.model.movie.soundtrack.song;

import com.company.persistance.model.AbstractBaseEntity;
import com.company.persistance.model.movie.soundtrack.personal.singer.Singer;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Song extends AbstractBaseEntity {

    private String name;
    private String duration;
    private List<Singer> singers = new LinkedList<>();

    public Song(int id, LocalDateTime createdAt, String name, String duration, List<Singer> singers) {
        super(id, createdAt);
        this.name = name;
        this.duration = duration;
        this.singers = singers;
    }

    public Song() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Singer> getSinger() {
        return singers;
    }

    public void setSinger(List<Singer> singers) {
        this.singers = singers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Song song = (Song) o;
        return Objects.equals(name, song.name) &&
                Objects.equals(duration, song.duration) &&
                Objects.equals(singers, song.singers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, duration, singers);
    }

    @Override
    public String toString() {
        return super.getId() + "_" + super.getId() + "_" + name + "_" + duration + "_" + singers;
    }
}
