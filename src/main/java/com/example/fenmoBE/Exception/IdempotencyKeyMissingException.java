package com.example.fenmoBE.Exception;

public class IdempotencyKeyMissingException extends RuntimeException {

    public IdempotencyKeyMissingException(String message) {
        super(message);
    }
}
