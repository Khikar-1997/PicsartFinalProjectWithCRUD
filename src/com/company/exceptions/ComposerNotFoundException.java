package com.company.exceptions;

public class ComposerNotFoundException extends RuntimeException {
    public ComposerNotFoundException(String massage) {
        super(massage);
    }
}
