package com.groupmergeworks.sirmeows.api.dto;

import lombok.Data;

@Data
public class MonitorRequest {
    private String type;
    private String reservationId;
    private String equipmentId;
}
