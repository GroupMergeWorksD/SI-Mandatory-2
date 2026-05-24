package com.groupmergeworks.sirmeows.service;

import com.groupmergeworks.sirmeows.domain.Equipment;
import com.groupmergeworks.sirmeows.domain.EquipmentStatus;
import com.groupmergeworks.sirmeows.domain.Reservation;
import com.groupmergeworks.sirmeows.exception.*;
import com.groupmergeworks.sirmeows.repository.EquipmentRepository;
import com.groupmergeworks.sirmeows.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentReservationService {

    private final EquipmentRepository equipmentRepository;
    private final ReservationRepository reservationRepository;

    public Reservation getReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + reservationId));
    }

    public Reservation createReservation(UUID equipmentId, OffsetDateTime startTime, OffsetDateTime endTime) {
        var equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found: " + equipmentId));

        if (!validateTimeRange(startTime, endTime)) {
            throw new InvalidReservationTimeException("Invalid time range. Start time must be before end time.");
        }

        if (!validateEquipmentCanBeReserved(equipment)) {
            throw new EquipmentUnavailableException("Equipment is not active and can not be reserved.");
        }

        if (!validateEquipmentAvailability(equipment, startTime, endTime)) {
            throw new ReservationConflictException("Equipment is not available at the requested time.");
        }

        var reservation = Reservation.builder().
                equipment(equipment)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        return reservationRepository.save(reservation);
    }

    private boolean validateTimeRange(OffsetDateTime startTime, OffsetDateTime endTime) {
        return startTime.isBefore(endTime);
    }

    private boolean validateEquipmentCanBeReserved(Equipment equipment) {
        return equipment.getStatus().equals(EquipmentStatus.ACTIVE);
    }

    private boolean validateEquipmentAvailability(Equipment equipment, OffsetDateTime startTime, OffsetDateTime endTime) {
        return !reservationRepository.existsByEquipmentAndStartTimeLessThanAndEndTimeGreaterThan(
                equipment,
                endTime,
                startTime
        );
    }
}
