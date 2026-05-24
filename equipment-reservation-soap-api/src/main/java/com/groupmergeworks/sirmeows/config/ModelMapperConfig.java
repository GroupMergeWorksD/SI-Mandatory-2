package com.groupmergeworks.sirmeows.config;

import com.groupmergeworks.sirmeows.domain.Reservation;
import com.groupmergeworks.sirmeows.soap.CreateReservationResponse;
import com.groupmergeworks.sirmeows.soap.Equipment;
import com.groupmergeworks.sirmeows.soap.GetReservationResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;
import java.util.GregorianCalendar;
import java.util.UUID;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new SoapModelMapper();

        addConverters(modelMapper);
        addReservationMappings(modelMapper);
        addEquipmentMappings(modelMapper);

        return modelMapper;
    }

    private void addConverters(ModelMapper modelMapper) {
        modelMapper.addConverter(
                xmlGregorianCalendarToOffsetDateTimeConverter(),
                Object.class,
                OffsetDateTime.class
        );
        modelMapper.addConverter(
                offsetDateTimeToXmlGregorianCalendarConverter(),
                OffsetDateTime.class,
                XMLGregorianCalendar.class
        );
    }

    private void addReservationMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(Reservation.class, com.groupmergeworks.sirmeows.soap.Reservation.class)
                .addMappings(mapper -> {
                    mapper.using(uuidToStringConverter())
                            .map(Reservation::getId, com.groupmergeworks.sirmeows.soap.Reservation::setReservationId);
                    mapper.using(offsetDateTimeToXmlGregorianCalendarConverter())
                            .map(Reservation::getStartTime, com.groupmergeworks.sirmeows.soap.Reservation::setStartTime);
                    mapper.using(offsetDateTimeToXmlGregorianCalendarConverter())
                            .map(Reservation::getEndTime, com.groupmergeworks.sirmeows.soap.Reservation::setEndTime);
                });

        modelMapper.addConverter(reservationToCreateReservationResponseConverter(modelMapper));
        modelMapper.addConverter(reservationToGetReservationResponseConverter(modelMapper));
    }

    private void addEquipmentMappings(ModelMapper modelMapper) {
        modelMapper.typeMap(com.groupmergeworks.sirmeows.domain.Equipment.class, Equipment.class)
                .addMappings(mapper -> mapper.using(uuidToStringConverter())
                        .map(com.groupmergeworks.sirmeows.domain.Equipment::getId, Equipment::setEquipmentId));
    }

    private Converter<UUID, String> uuidToStringConverter() {
        return context -> context.getSource() == null ? null : context.getSource().toString();
    }

    private Converter<Reservation, CreateReservationResponse> reservationToCreateReservationResponseConverter(ModelMapper modelMapper) {
        return context -> {
            if (context.getSource() == null) {
                return null;
            }

            var response = new CreateReservationResponse();
            response.setReservation(modelMapper.map(context.getSource(), com.groupmergeworks.sirmeows.soap.Reservation.class));
            return response;
        };
    }

    private Converter<Reservation, GetReservationResponse> reservationToGetReservationResponseConverter(ModelMapper modelMapper) {
        return context -> {
            if (context.getSource() == null) {
                return null;
            }

            var response = new GetReservationResponse();
            response.setReservation(modelMapper.map(context.getSource(), com.groupmergeworks.sirmeows.soap.Reservation.class));
            return response;
        };
    }

    private Converter<Object, OffsetDateTime> xmlGregorianCalendarToOffsetDateTimeConverter() {
        return context -> {
            if (context.getSource() == null) {
                return null;
            }

            if (context.getSource() instanceof XMLGregorianCalendar xmlGregorianCalendar) {
                return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toOffsetDateTime();
            }

            throw new IllegalArgumentException("Can not convert " + context.getSource().getClass().getName() + " to OffsetDateTime");
        };
    }

    private Converter<OffsetDateTime, XMLGregorianCalendar> offsetDateTimeToXmlGregorianCalendarConverter() {
        return context -> {
            if (context.getSource() == null) {
                return null;
            }

            var gregorianCalendar = GregorianCalendar.from(context.getSource().toZonedDateTime());

            try {
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            } catch (Exception exception) {
                throw new IllegalStateException("Failed to convert OffsetDateTime to XMLGregorianCalendar", exception);
            }
        };
    }
}
