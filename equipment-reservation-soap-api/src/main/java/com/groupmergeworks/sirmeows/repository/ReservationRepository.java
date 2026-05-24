package com.groupmergeworks.sirmeows.repository;

import com.groupmergeworks.sirmeows.domain.Equipment;
import com.groupmergeworks.sirmeows.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    boolean existsByEquipmentAndStartTimeLessThanAndEndTimeGreaterThan(
            Equipment equipment,
            OffsetDateTime endTime,
            OffsetDateTime startTime
    );
}
