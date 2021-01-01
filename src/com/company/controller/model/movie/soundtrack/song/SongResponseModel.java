package com.company.controller.model.movie.soundtrack.song;

import com.company.persistance.model.movie.soundtrack.personal.singer.Singer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class SongResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String name;
    private String duration;
    private List<Singer> singers;

    public SongResponseModel(int id, LocalDateTime createdAt, String name, String duration, List<Singer> singers) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.duration = duration;
        this.singers = singers;
    }

    public SongResponseModel() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + name + "," + duration + "," + singers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongResponseModel that = (SongResponseModel) o;
        return id == that.id && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name) && Objects.equals(duration, that.duration) && Objects.equals(singers, that.singers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, duration, singers);
    }

    @Override
    public String toString() {
        return id + "_" + createdAt + "_" + name + "_" + duration + "_" + singers;
    }
}
