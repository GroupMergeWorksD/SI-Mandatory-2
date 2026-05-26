package com.groupmergeworks.sirmeows.mapper;

import com.google.protobuf.Timestamp;
import com.groupmergeworks.sirmeows.domain.Reservation;
import com.groupmergeworks.sirmeows.grpc.GetReservationResponse;
import com.groupmergeworks.sirmeows.grpc.MonitorResponse;
import com.groupmergeworks.sirmeows.grpc.ReservationSnapshot;
import com.groupmergeworks.sirmeows.grpc.ReservationSummary;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class GrpcMapper {

    public GetReservationResponse toGetReservationResponse(Reservation reservation) {
        return GetReservationResponse.newBuilder()
                .setReservationId(reservation.getId().toString())
                .setEquipmentId(reservation.getEquipment().getId().toString())
                .setEquipmentName(reservation.getEquipment().getName())
                .setStartTime(toTimestamp(reservation.getStartTime()))
                .setEndTime(toTimestamp(reservation.getEndTime()))
                .build();
    }

    public MonitorResponse toSnapshotResponse(UUID equipmentId, List<Reservation> reservations) {
        var snapshotBuilder = ReservationSnapshot.newBuilder()
                .setEquipmentId(equipmentId.toString());

        reservations.forEach(reservation -> snapshotBuilder.addReservations(
                ReservationSummary.newBuilder()
                        .setReservationId(reservation.getId().toString())
                        .setStartTime(toTimestamp(reservation.getStartTime()))
                        .setEndTime(toTimestamp(reservation.getEndTime()))
                        .build()
        ));

        return MonitorResponse.newBuilder()
                .setSnapshot(snapshotBuilder.build())
                .build();
    }

    private Timestamp toTimestamp(OffsetDateTime dateTime) {
        Instant instant = dateTime.toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
