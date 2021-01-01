package com.company.persistance.model.movie.film;

import com.company.persistance.model.AbstractBaseEntity;
import com.company.persistance.model.movie.personal.actor.Actor;
import com.company.persistance.model.movie.personal.producer.Producer;
import com.company.persistance.model.movie.soundtrack.music.Soundtrack;
import com.company.persistance.model.movie.trailer.Trailer;

import java.time.LocalDateTime;
import java.util.*;

public class Movie extends AbstractBaseEntity {
    private String name;
    private String duration;
    private MovieGenre genre;
    private double rate;
    private Trailer trailer;
    private List<Actor> actors = new LinkedList<>();
    private List<Producer> producers = new LinkedList<>();
    private Soundtrack soundtrack;

    public Movie(int id, LocalDateTime createdAt, String name, String duration, MovieGenre genre, double rate, Trailer trailer, List<Actor> actors, List<Producer> producers, Soundtrack soundtrack) {
        super(id, createdAt);
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.rate = rate;
        this.trailer = trailer;
        this.actors = actors;
        this.producers = producers;
        this.soundtrack = soundtrack;
    }

    public Movie() {
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

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public Soundtrack getSoundtrack() {
        return soundtrack;
    }

    public void setSoundtrack(Soundtrack soundtrack) {
        this.soundtrack = soundtrack;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.rate, rate) == 0 && Objects.equals(name, movie.name) && Objects.equals(duration, movie.duration) && Objects.equals(genre, movie.genre) && Objects.equals(trailer, movie.trailer) && Objects.equals(actors, movie.actors) && Objects.equals(producers, movie.producers) && Objects.equals(soundtrack, movie.soundtrack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, genre, trailer, actors, producers, soundtrack, rate);
    }

    @Override
    public String toString() {
        return "Movie{" +
                super.getId() +
                super.getCreatedAt() +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", genre=" + genre +
                ", trailer=" + trailer +
                ", actors=" + actors +
                ", producers=" + producers +
                ", soundtrack=" + soundtrack +
                ", rate=" + rate +
                '}';
    }
}