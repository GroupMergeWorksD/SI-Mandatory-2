package com.groupmergeworks.sirmeows.validation;

import com.groupmergeworks.sirmeows.exception.InvalidRequestException;

import java.util.UUID;

public final class WebSocketRequestValidator {

    private WebSocketRequestValidator() {
    }

    public static UUID validateAndParseUuid(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidRequestException(fieldName + " is required.");
        }

        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new InvalidRequestException(fieldName + " must be a valid UUID.");
        }
    }
}
