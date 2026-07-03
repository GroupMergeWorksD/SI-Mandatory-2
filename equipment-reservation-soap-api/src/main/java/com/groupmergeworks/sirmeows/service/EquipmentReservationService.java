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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentReservationService {

    private final EquipmentRepository equipmentRepository;
    private final ReservationRepository reservationRepository;


    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
    public Reservation getReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

    public UUID deleteReservation(UUID reservationId) {
         var reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFoundException(reservationId));
         reservationRepository.delete(reservation);
         return reservation.getId();
    }

    public Reservation createReservation(UUID equipmentId, OffsetDateTime startTime, OffsetDateTime endTime) {
        var equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EquipmentNotFoundException(equipmentId));

        if (!validateTimeRange(startTime, endTime)) {
            throw new InvalidReservationTimeException();
        }

        if (!validateEquipmentCanBeReserved(equipment)) {
            throw new EquipmentUnavailableException(equipment.getId());
        }

        if (!validateEquipmentAvailability(equipment, startTime, endTime)) {
            throw new ReservationConflictException(equipment.getId());
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
