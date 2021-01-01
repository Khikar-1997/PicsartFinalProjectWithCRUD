package com.company.controller.model.movie.soundtrack.song;

import java.util.Objects;

public class SongRequestModel{
    private String name;
    private String duration;
    private int singerId;

    public SongRequestModel(String name, String duration, int singerId) {
        this.name = name;
        this.duration = duration;
        this.singerId = singerId;
    }

    public SongRequestModel() {
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

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongRequestModel that = (SongRequestModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(singerId, that.singerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, singerId);
    }

    @Override
    public String toString() {
        return "SongRequestModel{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", singerId=" + singerId +
                '}';
    }
}
