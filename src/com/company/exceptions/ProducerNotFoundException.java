package com.company.exceptions;

public class ProducerNotFoundException extends RuntimeException {
    public ProducerNotFoundException(String massage) {
        super(massage);
    }
}
