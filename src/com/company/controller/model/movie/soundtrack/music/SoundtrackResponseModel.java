package com.company.controller.model.movie.soundtrack.music;

import com.company.controller.model.movie.soundtrack.personal.composer.ComposerResponseModel;
import com.company.controller.model.movie.soundtrack.song.SongResponseModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class SoundtrackResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String duration;
    private SongResponseModel song;
    private ComposerResponseModel composer;

    public SoundtrackResponseModel(int id, LocalDateTime createdAt, String duration, SongResponseModel song, ComposerResponseModel composer) {
        this.id = id;
        this.createdAt = createdAt;
        this.duration = duration;
        this.song = song;
        this.composer = composer;
    }

    public SoundtrackResponseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SongResponseModel getSong() {
        return song;
    }

    public void setSong(SongResponseModel song) {
        this.song = song;
    }

    public ComposerResponseModel getComposer() {
        return composer;
    }

    public void setComposer(ComposerResponseModel composer) {
        this.composer = composer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + duration + "," + song + "," + composer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoundtrackResponseModel that = (SoundtrackResponseModel) o;
        return id == that.id && Objects.equals(createdAt, that.createdAt) && Objects.equals(duration, that.duration) && Objects.equals(song, that.song) && Objects.equals(composer, that.composer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, duration, song, composer);
    }

    @Override
    public String toString() {
        return id + "`" + createdAt + "`" + duration + "`" + song + "`" + composer;
    }
}