package com.groupmergeworks.sirmeows.api;

import com.groupmergeworks.sirmeows.service.EquipmentReservationService;
import com.groupmergeworks.sirmeows.soap.CreateReservationRequest;
import com.groupmergeworks.sirmeows.soap.CreateReservationResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.groupmergeworks.sirmeows.config.SoapConstants.NAMESPACE_URI;

@Endpoint
@RequiredArgsConstructor
public class EquipmentReservationEndpoint {

    private final EquipmentReservationService equipmentReservationService;
    private final ModelMapper modelMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createReservationRequest")
    @ResponsePayload
    public CreateReservationResponse createReservation(@RequestPayload CreateReservationRequest request) {
        var equipmentId = UUID.fromString(request.getEquipmentId());
        var startTime = modelMapper.map(request.getStartTime(), OffsetDateTime.class);
        var endTime = modelMapper.map(request.getEndTime(), OffsetDateTime.class);

        var reservation = equipmentReservationService.createReservation(equipmentId, startTime, endTime);

        return modelMapper.map(reservation, CreateReservationResponse.class);
    }
}
