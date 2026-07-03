package com.groupmergeworks.sirmeows.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReservationConflictException extends RuntimeException {

    private static final String message = "Equipment '%s' is not available at the requested time.";

    private final UUID equipmentId;

    public ReservationConflictException(UUID equipmentId) {
        super(String.format(message, equipmentId.toString()));
        this.equipmentId = equipmentId;
    }
}