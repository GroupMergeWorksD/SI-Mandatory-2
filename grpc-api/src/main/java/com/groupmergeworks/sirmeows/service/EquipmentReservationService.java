package com.groupmergeworks.sirmeows.service;

import com.groupmergeworks.sirmeows.domain.Reservation;
import com.groupmergeworks.sirmeows.exception.EquipmentNotFoundException;
import com.groupmergeworks.sirmeows.exception.ReservationNotFoundException;
import com.groupmergeworks.sirmeows.repository.EquipmentRepository;
import com.groupmergeworks.sirmeows.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Reservation> getReservationsByEquipmentId(UUID equipmentId) {
        equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found: " + equipmentId));

        return reservationRepository.findByEquipmentIdOrderByStartTimeAsc(equipmentId);
    }
}
