package com.groupmergeworks.sirmeows.exception;

public class InvalidUuidException extends RuntimeException {

    private static final String message = "%s must be a valid UUID.";

    public InvalidUuidException(String fieldName) {
        super(String.format(message, fieldName));
    }
}