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

import java.util.UUID;

@Endpoint
@RequiredArgsConstructor
public class EquipmentReservationEndpoint {

    private static final String NAMESPACE_URI = "http://groupmergeworks.com/sirmeows/equipment-reservation-soap-api";

    private final EquipmentReservationService equipmentReservationService;
    private final ModelMapper modelMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createReservationRequest")
    @ResponsePayload
    public CreateReservationResponse createReservation(@RequestPayload CreateReservationRequest request) {
        var equipmentId = UUID.fromString(request.getEquipmentId());
        var reservation = equipmentReservationService.createReservation(equipmentId);

        return modelMapper.map(reservation, CreateReservationResponse.class);
    }
}
