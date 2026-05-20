package com.groupmergeworks.sirmeows.service;

import com.groupmergeworks.sirmeows.domain.Equipment;
import com.groupmergeworks.sirmeows.domain.Reservation;
import com.groupmergeworks.sirmeows.exception.EquipmentNotFoundException;
import com.groupmergeworks.sirmeows.repository.EquipmentRepository;
import com.groupmergeworks.sirmeows.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentReservationService {

    private final EquipmentRepository equipmentRepository;
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(UUID equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found: " + equipmentId));

        Reservation reservation = new Reservation();
        reservation.setEquipment(equipment);

        return reservationRepository.save(reservation);
    }
}
