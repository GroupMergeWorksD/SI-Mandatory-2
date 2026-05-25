package com.groupmergeworks.sirmeows.config;

import com.groupmergeworks.sirmeows.soap.DeleteReservationResponse;
import com.groupmergeworks.sirmeows.soap.ListReservationsResponse;
import com.groupmergeworks.sirmeows.soap.Reservation;
import org.modelmapper.ModelMapper;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class SoapModelMapper extends ModelMapper {

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        if (destinationType.equals(OffsetDateTime.class) && source instanceof XMLGregorianCalendar xmlGregorianCalendar) {
            return destinationType.cast(toOffsetDateTime(xmlGregorianCalendar));
        }

        if (destinationType.equals(XMLGregorianCalendar.class) && source instanceof OffsetDateTime offsetDateTime) {
            return destinationType.cast(toXmlGregorianCalendar(offsetDateTime));
        }

        if (destinationType.equals(ListReservationsResponse.class) && source instanceof List<?> reservations) {
            return destinationType.cast(toListReservationsResponse(reservations));
        }

        if (destinationType.equals(DeleteReservationResponse.class) && source instanceof UUID reservationId) {
            return destinationType.cast(toDeleteReservationResponse(reservationId));
        }

        return super.map(source, destinationType);
    }

    private OffsetDateTime toOffsetDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
    }

    private XMLGregorianCalendar toXmlGregorianCalendar(OffsetDateTime offsetDateTime) {
        var gregorianCalendar = GregorianCalendar.from(offsetDateTime.toZonedDateTime());

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException exception) {
            throw new IllegalStateException("Failed to convert OffsetDateTime to XMLGregorianCalendar", exception);
        }
    }

    private ListReservationsResponse toListReservationsResponse(List<?> reservations) {
        var response = new ListReservationsResponse();

        for (var reservation : reservations) {
            response.getReservation().add(map(reservation, Reservation.class));
        }

        return response;
    }

    private DeleteReservationResponse toDeleteReservationResponse(UUID reservationId) {
        var response = new DeleteReservationResponse();
        response.setReservationId(reservationId.toString());
        return response;
    }
}
