package com.groupmergeworks.sirmeows.config;

import com.groupmergeworks.sirmeows.exception.EquipmentNotFoundException;
import com.groupmergeworks.sirmeows.exception.EquipmentUnavailableException;
import com.groupmergeworks.sirmeows.exception.InvalidRequestException;
import com.groupmergeworks.sirmeows.exception.InvalidReservationTimeException;
import com.groupmergeworks.sirmeows.exception.InvalidUuidException;
import com.groupmergeworks.sirmeows.exception.ReservationConflictException;
import com.groupmergeworks.sirmeows.exception.ReservationNotFoundException;
import com.groupmergeworks.sirmeows.soap.FaultDetail;
import com.groupmergeworks.sirmeows.soap.ObjectFactory;
import jakarta.xml.bind.JAXBElement;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.AbstractSoapFaultDefinitionExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;

import java.util.UUID;

public class SoapFaultExceptionResolver extends AbstractSoapFaultDefinitionExceptionResolver {

    private static final String unexpectedMessage = "An unexpected error occurred.";

    private final Jaxb2Marshaller marshaller;
    private final ObjectFactory objectFactory = new ObjectFactory();

    public SoapFaultExceptionResolver(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    protected SoapFaultDefinition getFaultDefinition(Object endpoint, Exception ex) {
        var definition = new SoapFaultDefinition();
        if (toFaultDetailElement(ex) != null) {
            definition.setFaultCode(SoapFaultDefinition.CLIENT);
            definition.setFaultStringOrReason(ex.getMessage());
        } else {
            definition.setFaultCode(SoapFaultDefinition.SERVER);
            definition.setFaultStringOrReason(unexpectedMessage);
        }
        return definition;
    }

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        var element = toFaultDetailElement(ex);
        if (element == null) {
            return;
        }
        SoapFaultDetail faultDetail = fault.addFaultDetail();
        marshaller.marshal(element, faultDetail.getResult());
    }

    private JAXBElement<FaultDetail> toFaultDetailElement(Exception ex) {
        return switch (ex) {
            case ReservationNotFoundException e -> objectFactory.createReservationNotFoundFault(detail(e.getMessage(), e.getReservationId()));
            case EquipmentNotFoundException e -> objectFactory.createEquipmentNotFoundFault(detail(e.getMessage(), e.getEquipmentId()));
            case EquipmentUnavailableException e -> objectFactory.createEquipmentUnavailableFault(detail(e.getMessage(), e.getEquipmentId()));
            case ReservationConflictException e -> objectFactory.createReservationConflictFault(detail(e.getMessage(), e.getEquipmentId()));
            case InvalidReservationTimeException e -> objectFactory.createInvalidReservationTimeFault(detail(e.getMessage(), null));
            case InvalidUuidException e -> objectFactory.createInvalidUuidFault(detail(e.getMessage(), null));
            case InvalidRequestException e -> objectFactory.createInvalidRequestFault(detail(e.getMessage(), null));
            default -> null;
        };
    }

    private FaultDetail detail(String message, UUID resourceId) {
        var faultDetail = objectFactory.createFaultDetail();
        faultDetail.setMessage(message);
        if (resourceId != null) {
            faultDetail.setResourceId(resourceId.toString());
        }
        return faultDetail;
    }
}