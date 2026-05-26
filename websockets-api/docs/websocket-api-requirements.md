# WebSocket API Requirements Coverage

This service implements the WebSocket portion of the assignment for the equipment reservation scenario.

## Mandatory requirements

- Same unary operation as gRPC:
    - `getReservation` by reservation id over WebSocket.
- Same bidirectional streaming-style operations as gRPC:
    - `subscribeEquipment`
    - `unsubscribeEquipment`
    - `ping`
- Database-backed data access:
    - Reads reservation and equipment data from MySQL/H2 via JPA repositories.
- Error handling:
    - Returns structured error messages with `type = "error"` and a `code`.
- Positive and negative tests:
    - Request/response payload examples are documented in `README.md`.

## Endpoint

- WebSocket endpoint: `ws://localhost:8085/ws/reservations`
