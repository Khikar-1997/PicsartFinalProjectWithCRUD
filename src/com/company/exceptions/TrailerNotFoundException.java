package com.company.exceptions;

public class TrailerNotFoundException extends RuntimeException {
    public TrailerNotFoundException(String massage) {
        super(massage);
    }
}
