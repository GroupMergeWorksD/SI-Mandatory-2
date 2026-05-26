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

## WebSocket API

