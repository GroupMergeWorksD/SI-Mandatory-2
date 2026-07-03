package com.groupmergeworks.sirmeows.exception;

public class InvalidReservationTimeException extends RuntimeException {

    private static final String message = "Invalid time range. Start time must be before end time.";

    public InvalidReservationTimeException() {
        super(message);
    }
}