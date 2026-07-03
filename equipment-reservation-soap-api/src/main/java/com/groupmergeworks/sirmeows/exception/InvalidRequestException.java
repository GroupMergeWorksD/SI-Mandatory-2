package com.groupmergeworks.sirmeows.exception;

public class InvalidRequestException extends RuntimeException {

    private static final String message = "%s is required.";

    public InvalidRequestException(String fieldName) {
        super(String.format(message, fieldName));
    }
}