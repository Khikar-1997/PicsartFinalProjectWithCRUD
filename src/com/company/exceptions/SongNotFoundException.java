package com.company.exceptions;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String massage) {
        super(massage);
    }
}
