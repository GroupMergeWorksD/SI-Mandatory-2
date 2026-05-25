package com.groupmergeworks.sirmeows.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class EquipmentUnavailableException extends RuntimeException {
    public EquipmentUnavailableException(String message) {
        super(message);
    }
}
