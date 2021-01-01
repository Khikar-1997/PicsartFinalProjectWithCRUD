package com.company.controller.model.movie.soundtrack.music;

import java.util.Objects;

public class SoundtrackRequestModel {
    private String duration;
    private int songId;
    private int ComposerId;

    public SoundtrackRequestModel(String duration, int songId, int composerId) {
        this.duration = duration;
        this.songId = songId;
        ComposerId = composerId;
    }

    public SoundtrackRequestModel() {
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getComposerId() {
        return ComposerId;
    }

    public void setComposerId(int composerId) {
        ComposerId = composerId;
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
        SoundtrackRequestModel that = (SoundtrackRequestModel) o;
        return Objects.equals(duration, that.duration) &&
                Objects.equals(songId, that.songId) &&
                Objects.equals(ComposerId, that.ComposerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, songId, ComposerId);
    }

    @Override
    public String toString() {
        return "SoundtrackRequestModel{" +
                "duration='" + duration + '\'' +
                ", songId=" + songId +
                ", ComposerId=" + ComposerId +
                '}';
    }
}