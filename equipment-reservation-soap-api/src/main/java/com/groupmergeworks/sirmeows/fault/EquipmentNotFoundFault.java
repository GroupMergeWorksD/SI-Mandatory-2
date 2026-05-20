package com.groupmergeworks.sirmeows.fault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentNotFoundFault {
    private Long equipmentId;
    private String message;
}
