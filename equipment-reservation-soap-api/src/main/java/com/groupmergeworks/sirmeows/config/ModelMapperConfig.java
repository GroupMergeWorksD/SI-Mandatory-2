package com.groupmergeworks.sirmeows.config;

import com.groupmergeworks.sirmeows.domain.Reservation;
import com.groupmergeworks.sirmeows.soap.CreateReservationResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        Converter<UUID, String> uuidToString = context ->
                context.getSource() == null ? null : context.getSource().toString();

        modelMapper.typeMap(Reservation.class, CreateReservationResponse.class)
                .addMappings(mapper -> {
                    mapper.using(uuidToString)
                            .map(Reservation::getId, CreateReservationResponse::setReservationId);
                    mapper.using(uuidToString)
                            .map(source -> source.getEquipment().getId(), CreateReservationResponse::setEquipmentId);
                });

        return modelMapper;
    }
}
