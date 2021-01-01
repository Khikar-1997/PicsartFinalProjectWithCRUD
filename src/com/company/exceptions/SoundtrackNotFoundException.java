package com.company.exceptions;

public class SoundtrackNotFoundException extends RuntimeException {
    public SoundtrackNotFoundException(String massage) {
        super(massage);
    }
}
