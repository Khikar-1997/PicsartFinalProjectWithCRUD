package com.company.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String massage) {
        super(massage);
    }
}
