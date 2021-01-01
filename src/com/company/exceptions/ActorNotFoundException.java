package com.company.exceptions;

public class ActorNotFoundException extends RuntimeException {
    public ActorNotFoundException(String massage) {
        super(massage);
    }
}