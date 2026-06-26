package com.groupmergeworks.sirmeows.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EquipmentUnavailableException extends RuntimeException {

    private static final String message = "Equipment '%s' is not active and can not be reserved.";

    private final UUID equipmentId;

    public EquipmentUnavailableException(UUID equipmentId) {
        super(String.format(message, equipmentId.toString()));
        this.equipmentId = equipmentId;
    }
}