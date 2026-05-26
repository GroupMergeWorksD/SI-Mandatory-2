package com.groupmergeworks.sirmeows.mapper;

import com.groupmergeworks.sirmeows.api.dto.MonitorResponse;
import com.groupmergeworks.sirmeows.api.dto.ReservationSummaryDto;
import com.groupmergeworks.sirmeows.domain.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class WebSocketMapper {

    public ReservationSummaryDto toReservationSummary(Reservation reservation) {
        return ReservationSummaryDto.builder()
                .reservationId(reservation.getId())
                .equipmentId(reservation.getEquipment().getId())
                .equipmentName(reservation.getEquipment().getName())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .build();
    }

    public MonitorResponse toUnaryReservationResponse(Reservation reservation) {
        return MonitorResponse.builder()
                .type("reservation")
                .reservation(toReservationSummary(reservation))
                .build();
    }

    public MonitorResponse toSnapshotResponse(UUID equipmentId, List<Reservation> reservations) {
        return MonitorResponse.builder()
                .type("snapshot")
                .equipmentId(equipmentId)
                .reservations(reservations.stream().map(this::toReservationSummary).toList())
                .build();
    }
}
