package com.groupmergeworks.sirmeows.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupmergeworks.sirmeows.api.dto.MonitorRequest;
import com.groupmergeworks.sirmeows.api.dto.MonitorResponse;
import com.groupmergeworks.sirmeows.exception.EquipmentNotFoundException;
import com.groupmergeworks.sirmeows.exception.InvalidRequestException;
import com.groupmergeworks.sirmeows.exception.ReservationNotFoundException;
import com.groupmergeworks.sirmeows.mapper.WebSocketMapper;
import com.groupmergeworks.sirmeows.service.EquipmentReservationService;
import com.groupmergeworks.sirmeows.validation.WebSocketRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class EquipmentReservationWebSocketHandler extends TextWebSocketHandler {

    private static final String TYPE_GET_RESERVATION = "getReservation";
    private static final String TYPE_SUBSCRIBE = "subscribeEquipment";
    private static final String TYPE_UNSUBSCRIBE = "unsubscribeEquipment";
    private static final String TYPE_PING = "ping";

    private final EquipmentReservationService equipmentReservationService;
    private final WebSocketMapper webSocketMapper;
    private final ObjectMapper objectMapper;

    private final Map<String, Set<UUID>> subscriptionsBySession = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        subscriptionsBySession.put(session.getId(), ConcurrentHashMap.newKeySet());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            var request = objectMapper.readValue(message.getPayload(), MonitorRequest.class);
            if (request.getType() == null || request.getType().isBlank()) {
                sendResponse(session, errorResponse("INVALID_ARGUMENT", "type is required."));
                return;
            }

            switch (request.getType()) {
                case TYPE_GET_RESERVATION -> handleGetReservation(session, request);
                case TYPE_SUBSCRIBE -> handleSubscribe(session, request);
                case TYPE_UNSUBSCRIBE -> handleUnsubscribe(session, request);
                case TYPE_PING -> sendResponse(session, MonitorResponse.builder()
                        .type("ack")
                        .message("pong")
                        .build());
                default -> sendResponse(session, errorResponse(
                        "INVALID_ARGUMENT",
                        "Unsupported type. Use getReservation, subscribeEquipment, unsubscribeEquipment, or ping."
                ));
            }
        } catch (JsonProcessingException exception) {
            sendResponse(session, errorResponse("INVALID_ARGUMENT", "Request payload must be valid JSON."));
        } catch (InvalidRequestException exception) {
            sendResponse(session, errorResponse("INVALID_ARGUMENT", exception.getMessage()));
        } catch (ReservationNotFoundException | EquipmentNotFoundException exception) {
            sendResponse(session, errorResponse("NOT_FOUND", exception.getMessage()));
        } catch (Exception exception) {
            sendResponse(session, errorResponse("INTERNAL", "Unexpected server error."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        subscriptionsBySession.remove(session.getId());
    }

    private void handleGetReservation(WebSocketSession session, MonitorRequest request) throws IOException {
        var reservationId = WebSocketRequestValidator.validateAndParseUuid(request.getReservationId(), "reservationId");
        var reservation = equipmentReservationService.getReservation(reservationId);
        sendResponse(session, webSocketMapper.toUnaryReservationResponse(reservation));
    }

    private void handleSubscribe(WebSocketSession session, MonitorRequest request) throws IOException {
        var equipmentId = WebSocketRequestValidator.validateAndParseUuid(request.getEquipmentId(), "equipmentId");
        var reservations = equipmentReservationService.getReservationsByEquipmentId(equipmentId);
        subscriptionsBySession.computeIfAbsent(session.getId(), ignored -> ConcurrentHashMap.newKeySet()).add(equipmentId);

        sendResponse(session, webSocketMapper.toSnapshotResponse(equipmentId, reservations));
        sendResponse(session, MonitorResponse.builder()
                .type("ack")
                .message("Subscribed to equipment " + equipmentId)
                .build());
    }

    private void handleUnsubscribe(WebSocketSession session, MonitorRequest request) throws IOException {
        var equipmentId = WebSocketRequestValidator.validateAndParseUuid(request.getEquipmentId(), "equipmentId");
        subscriptionsBySession.computeIfAbsent(session.getId(), ignored -> ConcurrentHashMap.newKeySet()).remove(equipmentId);

        sendResponse(session, MonitorResponse.builder()
                .type("ack")
                .message("Unsubscribed from equipment " + equipmentId)
                .build());
    }

    private MonitorResponse errorResponse(String code, String message) {
        return MonitorResponse.builder()
                .type("error")
                .code(code)
                .message(message)
                .build();
    }

    private void sendResponse(WebSocketSession session, MonitorResponse response) throws IOException {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
    }
}
