package com.groupmergeworks.sirmeows.validation;

import com.groupmergeworks.sirmeows.exception.InvalidRequestException;
import com.groupmergeworks.sirmeows.exception.InvalidUuidException;

import java.util.UUID;

public final class SoapRequestValidator {

    private SoapRequestValidator() {
    }

    public static UUID validateAndParseUuid(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidRequestException(fieldName);
        }

        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new InvalidUuidException(fieldName);
        }
    }

    public static void validateRequired(Object value, String fieldName) {
        if (value == null) {
            throw new InvalidRequestException(fieldName);
        }
    }
}