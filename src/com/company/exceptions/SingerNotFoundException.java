package com.company.exceptions;

public class SingerNotFoundException extends RuntimeException {
    public SingerNotFoundException(String massage) {
        super(massage);
    }
}
