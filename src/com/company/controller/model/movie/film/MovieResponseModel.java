package com.company.controller.model.movie.film;

import com.company.persistance.model.movie.film.MovieGenre;
import com.company.persistance.model.movie.personal.actor.Actor;
import com.company.persistance.model.movie.personal.producer.Producer;
import com.company.controller.model.movie.soundtrack.music.SoundtrackResponseModel;
import com.company.controller.model.movie.trailer.TrailerResponseModel;

import java.time.LocalDateTime;
import java.util.*;

public class MovieResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String name;
    private String duration;
    private MovieGenre genre;
    private double rate;
    private List<Actor> actors = new LinkedList<>();
    private List<Producer> producers = new LinkedList<>();
    private SoundtrackResponseModel soundtrack;
    private TrailerResponseModel trailer;

    public MovieResponseModel(int id, LocalDateTime createdAt, String name, String duration, MovieGenre genre, double rate, List<Actor> actors, List<Producer> producers, SoundtrackResponseModel soundtrack, TrailerResponseModel trailer) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.actors = actors;
        this.producers = producers;
        this.soundtrack = soundtrack;
        this.trailer = trailer;
    }

    public MovieResponseModel() {
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

    public SoundtrackResponseModel getSoundtrack() {
        return soundtrack;
    }

    public void setSoundtrack(SoundtrackResponseModel soundtrack) {
        this.soundtrack = soundtrack;
    }

    public TrailerResponseModel getTrailer() {
        return trailer;
    }

    public void setTrailer(TrailerResponseModel trailer) {
        this.trailer = trailer;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + name + "," + duration + "," + genre + ","  + rate + "," + actors + "," + producers + "," + soundtrack + "," + trailer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieResponseModel that = (MovieResponseModel) o;
        return id == that.id && Double.compare(that.rate, rate) == 0 && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name) && Objects.equals(duration, that.duration) && genre == that.genre && Objects.equals(actors, that.actors) && Objects.equals(producers, that.producers) && Objects.equals(soundtrack, that.soundtrack) && Objects.equals(trailer, that.trailer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, duration, genre, rate, actors, producers, soundtrack, trailer);
    }

    @Override
    public String toString() {
        return "MovieResponseModel{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", genre=" + genre +
                ", rate=" + rate +
                ", actors=" + actors +
                ", producers=" + producers +
                ", soundtrack=" + soundtrack +
                ", trailer=" + trailer +
                '}';
    }
}
