package com.company.persistance.model.movie.trailer;

import com.company.persistance.model.AbstractBaseEntity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Trailer extends AbstractBaseEntity {

    private String duration;

    public Trailer(int id, LocalDateTime createdAt, String duration) {
        super(id, createdAt);
        this.duration = duration;
    }

    public Trailer() {
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
        Trailer trailer = (Trailer) o;
        return Objects.equals(duration, trailer.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration);
    }

    @Override
    public String toString() {
        return super.getId() + "_" + super.getCreatedAt() + "_" + duration;
    }
}
