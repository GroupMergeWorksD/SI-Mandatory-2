# gRPC API Requirements

This document condenses the assignment requirements that apply to the gRPC API in this project.

## General Requirements

- The API must use database-stored information.
- The API must protect against common security issues where applicable, including SQL injection, XSS, and CSRF.
- A Postman collection or similar test suite must be provided.
- The test suite must include both positive and negative requests.
- Source code and test collections must be part of the delivery.

## gRPC-Specific Requirements

- The gRPC API must expose protobuf-generated service definitions.
- The gRPC API must implement at least 1 unary RPC.
- The gRPC API must implement at least 1 bidirectional streaming RPC.
- A Postman collection or similar test suite must be provided.

## Project Scope

- Business domain: equipment reservation.
- gRPC endpoint: `localhost:8084`.
- Proto file: `src/main/proto/equipment_reservation_service.proto`.
- Database: MySQL database `equipment_reservation_db`.

## gRPC Operations

- `GetReservation` (unary): read one reservation by ID.
- `MonitorReservations` (bidirectional streaming): subscribe to equipment IDs and receive reservation snapshots, acknowledgements, and stream-level errors.

## Error Handling

- Unary `GetReservation` returns gRPC status `INVALID_ARGUMENT` for malformed or missing IDs.
- Unary `GetReservation` returns gRPC status `NOT_FOUND` for missing reservations.
- Streaming `MonitorReservations` returns `ErrorInfo` messages for invalid requests and missing equipment without closing the stream.

## Testing Expectations

- Import the proto file into Postman or use `grpcurl`.
- Each RPC has positive and negative test cases.
- Database seed data supports repeatable testing.
