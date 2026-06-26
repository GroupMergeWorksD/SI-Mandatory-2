package com.groupmergeworks.sirmeows.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EquipmentNotFoundException extends RuntimeException {

    private static final String message = "Equipment not found: %s";

    private final UUID equipmentId;

    public EquipmentNotFoundException(UUID equipmentId) {
        super(String.format(message, equipmentId.toString()));
        this.equipmentId = equipmentId;
    }
}