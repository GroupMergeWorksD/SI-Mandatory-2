package com.groupmergeworks.sirmeows.repository;

import com.groupmergeworks.sirmeows.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
}
