package com.groupmergeworks.sirmeows.api;

import com.groupmergeworks.sirmeows.exception.EquipmentNotFoundException;
import com.groupmergeworks.sirmeows.exception.InvalidRequestException;
import com.groupmergeworks.sirmeows.exception.ReservationNotFoundException;
import com.groupmergeworks.sirmeows.grpc.Ack;
import com.groupmergeworks.sirmeows.grpc.ErrorInfo;
import com.groupmergeworks.sirmeows.grpc.EquipmentReservationServiceGrpc;
import com.groupmergeworks.sirmeows.grpc.GetReservationRequest;
import com.groupmergeworks.sirmeows.grpc.GetReservationResponse;
import com.groupmergeworks.sirmeows.grpc.MonitorRequest;
import com.groupmergeworks.sirmeows.grpc.MonitorResponse;
import com.groupmergeworks.sirmeows.mapper.GrpcMapper;
import com.groupmergeworks.sirmeows.service.EquipmentReservationService;
import com.groupmergeworks.sirmeows.validation.GrpcRequestValidator;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@GrpcService
@RequiredArgsConstructor
public class EquipmentReservationGrpcService extends EquipmentReservationServiceGrpc.EquipmentReservationServiceImplBase {

    private final EquipmentReservationService equipmentReservationService;
    private final GrpcMapper grpcMapper;

    @Override
    public void getReservation(GetReservationRequest request, StreamObserver<GetReservationResponse> responseObserver) {
        try {
            var reservationId = GrpcRequestValidator.validateAndParseUuid(request.getReservationId(), "reservationId");
            var reservation = equipmentReservationService.getReservation(reservationId);
            responseObserver.onNext(grpcMapper.toGetReservationResponse(reservation));
            responseObserver.onCompleted();
        } catch (InvalidRequestException exception) {
            responseObserver.onError(
                    Status.INVALID_ARGUMENT.withDescription(exception.getMessage()).asRuntimeException()
            );
        } catch (ReservationNotFoundException exception) {
            responseObserver.onError(
                    Status.NOT_FOUND.withDescription(exception.getMessage()).asRuntimeException()
            );
        }
    }

    @Override
    public StreamObserver<MonitorRequest> monitorReservations(StreamObserver<MonitorResponse> responseObserver) {
        Set<UUID> subscriptions = ConcurrentHashMap.newKeySet();

        return new StreamObserver<>() {
            @Override
            public void onNext(MonitorRequest request) {
                try {
                    switch (request.getRequestCase()) {
                        case SUBSCRIBE_EQUIPMENT -> handleSubscribe(request, subscriptions, responseObserver);
                        case UNSUBSCRIBE_EQUIPMENT -> handleUnsubscribe(request, subscriptions, responseObserver);
                        case PING -> responseObserver.onNext(MonitorResponse.newBuilder()
                                .setAck(Ack.newBuilder().setMessage("pong").build())
                                .build());
                        case REQUEST_NOT_SET -> responseObserver.onNext(errorResponse(
                                "INVALID_ARGUMENT",
                                "MonitorRequest must include subscribe_equipment, unsubscribe_equipment, or ping."
                        ));
                        default -> responseObserver.onNext(errorResponse(
                                "INVALID_ARGUMENT",
                                "Unsupported monitor request."
                        ));
                    }
                } catch (InvalidRequestException exception) {
                    responseObserver.onNext(errorResponse("INVALID_ARGUMENT", exception.getMessage()));
                } catch (EquipmentNotFoundException exception) {
                    responseObserver.onNext(errorResponse("NOT_FOUND", exception.getMessage()));
                }
            }

            @Override
            public void onError(Throwable throwable) {
                subscriptions.clear();
            }

            @Override
            public void onCompleted() {
                subscriptions.clear();
                responseObserver.onCompleted();
            }
        };
    }

    private void handleSubscribe(
            MonitorRequest request,
            Set<UUID> subscriptions,
            StreamObserver<MonitorResponse> responseObserver
    ) {
        var equipmentId = GrpcRequestValidator.validateAndParseUuid(
                request.getSubscribeEquipment().getEquipmentId(),
                "equipmentId"
        );

        var reservations = equipmentReservationService.getReservationsByEquipmentId(equipmentId);
        subscriptions.add(equipmentId);

        responseObserver.onNext(grpcMapper.toSnapshotResponse(equipmentId, reservations));
        responseObserver.onNext(MonitorResponse.newBuilder()
                .setAck(Ack.newBuilder()
                        .setMessage("Subscribed to equipment " + equipmentId)
                        .build())
                .build());
    }

    private void handleUnsubscribe(
            MonitorRequest request,
            Set<UUID> subscriptions,
            StreamObserver<MonitorResponse> responseObserver
    ) {
        var equipmentId = GrpcRequestValidator.validateAndParseUuid(
                request.getUnsubscribeEquipment().getEquipmentId(),
                "equipmentId"
        );

        subscriptions.remove(equipmentId);
        responseObserver.onNext(MonitorResponse.newBuilder()
                .setAck(Ack.newBuilder()
                        .setMessage("Unsubscribed from equipment " + equipmentId)
                        .build())
                .build());
    }

    private MonitorResponse errorResponse(String code, String message) {
        return MonitorResponse.newBuilder()
                .setError(ErrorInfo.newBuilder()
                        .setCode(code)
                        .setMessage(message)
                        .build())
                .build();
    }
}
