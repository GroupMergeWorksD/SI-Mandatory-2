package com.groupmergeworks.sirmeows.api;

import com.groupmergeworks.sirmeows.service.EquipmentReservationService;
import com.groupmergeworks.sirmeows.soap.CreateReservationRequest;
import com.groupmergeworks.sirmeows.soap.CreateReservationResponse;
import com.groupmergeworks.sirmeows.soap.DeleteReservationRequest;
import com.groupmergeworks.sirmeows.soap.DeleteReservationResponse;
import com.groupmergeworks.sirmeows.soap.GetReservationRequest;
import com.groupmergeworks.sirmeows.soap.GetReservationResponse;
import com.groupmergeworks.sirmeows.soap.ListReservationsRequest;
import com.groupmergeworks.sirmeows.soap.ListReservationsResponse;
import com.groupmergeworks.sirmeows.validation.SoapRequestValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.OffsetDateTime;

import static com.groupmergeworks.sirmeows.config.SoapConstants.NAMESPACE_URI;

@Endpoint
@RequiredArgsConstructor
public class EquipmentReservationEndpoint {

    private final EquipmentReservationService equipmentReservationService;
    private final ModelMapper modelMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createReservationRequest")
    @ResponsePayload
    public CreateReservationResponse createReservation(@RequestPayload CreateReservationRequest request) {
        var equipmentId = SoapRequestValidator.validateAndParseUuid(request.getEquipmentId(), "equipmentId");
        SoapRequestValidator.validateRequired(request.getStartTime(), "startTime");
        SoapRequestValidator.validateRequired(request.getEndTime(), "endTime");

        var startTime = modelMapper.map(request.getStartTime(), OffsetDateTime.class);
        var endTime = modelMapper.map(request.getEndTime(), OffsetDateTime.class);

        var reservation = equipmentReservationService.createReservation(equipmentId, startTime, endTime);

        return modelMapper.map(reservation, CreateReservationResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReservation(@RequestPayload GetReservationRequest request) {
        var reservationId = SoapRequestValidator.validateAndParseUuid(request.getReservationId(), "reservationId");
        var reservation = equipmentReservationService.getReservation(reservationId);

        return modelMapper.map(reservation, GetReservationResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listReservationsRequest")
    @ResponsePayload
    public ListReservationsResponse listReservations(@RequestPayload ListReservationsRequest request) {
        var reservations = equipmentReservationService.getReservations();

        return modelMapper.map(reservations, ListReservationsResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteReservationRequest")
    @ResponsePayload
    public DeleteReservationResponse deleteReservation(@RequestPayload DeleteReservationRequest request) {
        var reservationId = SoapRequestValidator.validateAndParseUuid(request.getReservationId(), "reservationId");
        var deletedReservationId = equipmentReservationService.deleteReservation(reservationId);

        return modelMapper.map(deletedReservationId, DeleteReservationResponse.class);
    }
}
