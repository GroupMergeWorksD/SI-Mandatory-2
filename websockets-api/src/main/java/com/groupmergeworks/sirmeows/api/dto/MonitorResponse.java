package com.groupmergeworks.sirmeows.api.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record MonitorResponse(
        String type,
        String message,
        String code,
        UUID equipmentId,
        ReservationSummaryDto reservation,
        List<ReservationSummaryDto> reservations
) {
}
