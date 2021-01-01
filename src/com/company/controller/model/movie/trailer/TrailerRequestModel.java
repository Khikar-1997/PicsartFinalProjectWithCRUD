package com.company.controller.model.movie.trailer;

import java.util.Objects;

public class TrailerRequestModel {
    private String duration;

    public TrailerRequestModel(String duration) {
        this.duration = duration;
    }

    public TrailerRequestModel() {
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
        TrailerRequestModel that = (TrailerRequestModel) o;
        return Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }

    @Override
    public String toString() {
        return "TrailerRequestModel{" +
                "duration='" + duration + '\'' +
                '}';
    }
}