package com.groupmergeworks.sirmeows.config;

import com.groupmergeworks.sirmeows.api.EquipmentReservationWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final EquipmentReservationWebSocketHandler equipmentReservationWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(equipmentReservationWebSocketHandler, "/ws/reservations")
                .setAllowedOriginPatterns("*");
    }
}
