package com.groupmergeworks.sirmeows.repository;

import com.groupmergeworks.sirmeows.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
}
