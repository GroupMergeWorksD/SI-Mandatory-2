package com.groupmergeworks.sirmeows.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReservationNotFoundException extends RuntimeException {

    private static final String message = "Reservation not found: %s";

    private final UUID reservationId;

    public ReservationNotFoundException(UUID reservationId) {
        super(String.format(message, reservationId.toString()));
        this.reservationId = reservationId;
    }
}
