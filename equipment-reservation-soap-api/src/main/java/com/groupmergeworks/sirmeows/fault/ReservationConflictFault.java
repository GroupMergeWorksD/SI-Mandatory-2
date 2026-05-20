package com.groupmergeworks.sirmeows.fault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationConflictFault {
    private Long equipmentId;
    private LocalDateTime requestedStartTime;
    private LocalDateTime requestedEndTime;
    private String message;
}
