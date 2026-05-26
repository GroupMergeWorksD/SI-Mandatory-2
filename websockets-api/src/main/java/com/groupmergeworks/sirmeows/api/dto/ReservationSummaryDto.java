package com.groupmergeworks.sirmeows.api.dto;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ReservationSummaryDto(
        UUID reservationId,
        UUID equipmentId,
        String equipmentName,
        OffsetDateTime startTime,
        OffsetDateTime endTime
) {
}
