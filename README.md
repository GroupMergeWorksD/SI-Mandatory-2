# SI Mandatory 2

Multi-service system for the mandatory assignment.

## Services

- `rest-api`: REST API on port `8081`
- `equipment-reservation-soap-api`: Spring Boot SOAP API on port `8082`
- `graphql-api`: GraphQL API on port `8083`
- `grpc-api`: gRPC API on port `8084`
- `websocket-api`: WebSocket API on port `8085`

## Docker

The applications and their dependencies are dockerized, so no local database or service installation is needed. Create a local `.env` file using `dotenv-template` as reference, then start the system with Docker Compose from parent repo.

To start the system:
```bash
docker compose up -d
```

To do a clean restart including database reset:

```bash
docker compose down -v
docker compose up --build -d
```

## REST API

## SOAP API

WSDL:

```text
http://localhost:8082/ws/equipment-reservation-soap-api.wsdl
```

SOAP endpoint:

```text
http://localhost:8082/ws
```

Postman collection:

```text
equipment-reservation-soap-api/postman/equipment-reservation-soap-api.postman_collection.json
```

SOAP API database:

```text
equipment_reservation_db
```

## GraphQL API

## gRPC API

gRPC endpoint:

```text
localhost:8084
```

Proto file:

```text
grpc-api/src/main/proto/equipment_reservation_service.proto
```

Postman collection:

```text
grpc-api/postman/grpc-api.postman_collection.json
```

gRPC API database:

```text
equipment_reservation_db
```

Example `grpcurl` commands:

```bash
grpcurl -plaintext -d '{"reservation_id":"2d8f4162-2b85-48d1-8b59-d2be0a6e512a"}' localhost:8084 equipmentreservation.EquipmentReservationService/GetReservation
```

## WebSocket API

WebSocket endpoint:

```text
ws://localhost:8085/ws/reservations
```

WebSocket module:

```text
websockets-api
```

