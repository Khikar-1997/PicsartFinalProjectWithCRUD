package com.company.controller.model.movie.trailer;

import java.time.LocalDateTime;
import java.util.Objects;

public class TrailerResponseModel {
    private int id;
    private LocalDateTime createdAt;
    private String duration;

    public TrailerResponseModel(int id, LocalDateTime createdAt, String duration) {
        this.id = id;
        this.createdAt = createdAt;
        this.duration = duration;
    }

    public TrailerResponseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String toStringWithoutTextInfo() {
        return id + "," + createdAt + "," + duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrailerResponseModel)) return false;
        TrailerResponseModel that = (TrailerResponseModel) o;
        return getId() == that.getId() && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getDuration(), that.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getDuration());
    }


    @Override
    public String toString() {
        return id + "_" + createdAt + "_" + duration;
    }
}
