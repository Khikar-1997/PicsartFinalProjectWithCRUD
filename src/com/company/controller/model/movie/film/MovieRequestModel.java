package com.company.controller.model.movie.film;

import com.company.persistance.model.movie.film.MovieGenre;

import java.util.Objects;

public class MovieRequestModel {
    private String name;
    private String duration;
    private MovieGenre genre;
    private double rate;
    private int trailerId;
    private int actorId;
    private int producerId;
    private int soundtrackId;

    public MovieRequestModel(String name, String duration, MovieGenre genre, double rate, int trailerId, int actorId, int producerId, int soundtrackId) {
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.rate = rate;
        this.trailerId = trailerId;
        this.actorId = actorId;
        this.producerId = producerId;
        this.soundtrackId = soundtrackId;
    }

    public MovieRequestModel() {
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(int trailerId) {
        this.trailerId = trailerId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public int getSoundtrackId() {
        return soundtrackId;
    }

    public void setSoundtrackId(int soundtrackId) {
        this.soundtrackId = soundtrackId;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieRequestModel)) return false;
        MovieRequestModel that = (MovieRequestModel) o;
        return getRate() == that.getRate() && Objects.equals(getName(), that.getName()) && Objects.equals(getDuration(), that.getDuration()) && getGenre() == that.getGenre() && Objects.equals(getTrailerId(), that.getTrailerId()) && Objects.equals(getActorId(), that.getActorId()) && Objects.equals(getProducerId(), that.getProducerId()) && Objects.equals(getSoundtrackId(), that.getSoundtrackId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDuration(), getGenre(), getRate(), getTrailerId(), getActorId(), getProducerId(), getSoundtrackId());
    }

    @Override
    public String toString() {
        return "MovieRequestModel{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", genre=" + genre +
                ", rate=" + rate +
                ", trailerId=" + trailerId +
                ", actorId=" + actorId +
                ", producerId=" + producerId +
                ", soundtrackId=" + soundtrackId +
                '}';
    }
}
