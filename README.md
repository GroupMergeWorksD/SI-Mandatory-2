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

Base URL:

```
texthttp://localhost:8081/api/v1
```

Postman collection:

```text
student-course-rest-api/postman/SI-REST.postman_collection.json
```

Swagger UI:

```text
http://localhost:8081/swagger-ui.html
```

DB data script:
```
student-course-rest-api/src/main/resources/data/dataScript.sql
```

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

Base URL:
```text
http://localhost:8083/graphql
```

Postman collection:
```text
graphql-api/postman/GraphQL API.postman_collection.json
```

GraphQL API database:
```text
graphql-api/db/app.db
```

## gRPC API

Proto file:

```text
grpc-api/src/main/proto/equipment_reservation_service.proto
```

gRPC endpoint:

```text
localhost:8084
```

Postman collection:

```text
grpc-api/postman/grpc-api.postman_collection.json
```

gRPC API database:

```text
equipment_reservation_db
```

## WebSocket API

WebSocket endpoint:

```text
ws://localhost:8085/ws/reservations
```

Postman collection:

```text
websockets-api/postman/websockets-api.postman_collection.json
```

WebSocket API database:

```text
equipment_reservation_db
```

