### Running application in Docker
Commands for building the image and running the container.

Open a terminal in the folder `websockets-api` and run the following commands:
1. #### Build image: `docker build -t websockets-api-image .`
2. #### Run container: `docker run --rm --name websockets-api -p 8085:8085 -e WS_MYSQL_ROOT_USER=root -e WS_MYSQL_ROOT_PASSWORD=yourpassword -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/equipment_reservation_db websockets-api-image`

WebSocket endpoint:
`ws://localhost:8085/ws/reservations`

A MySQL database must already be running and reachable at the URL above.

### Running with Docker Compose (this module)
From the `websockets-api` folder, copy `dotenv-template` to `.env` if you want a custom MySQL password, then run:

```bash
docker compose up -d
```

This starts a dedicated MySQL container (`websocket-mysql` on host port `3309`) and the WebSocket API on port `8085`.

To rebuild and restart:

```bash
docker compose down -v
docker compose up --build -d
```

### Running locally with Maven
Requires **JDK 24** and **Maven**. MySQL must be running with database `equipment_reservation_db`.

```bash
set WS_MYSQL_ROOT_USER=root
set WS_MYSQL_ROOT_PASSWORD=yourpassword
mvn spring-boot:run
```

On Linux/macOS, use `export` instead of `set`.

### Request format
Send JSON messages over the WebSocket connection:

- Unary operation:
`{"type":"getReservation","reservationId":"2d8f4162-2b85-48d1-8b59-d2be0a6e512a"}`
- Stream-style operations:
`{"type":"subscribeEquipment","equipmentId":"8f6c7bbd-3f1f-4b6f-9bcb-84db99411f2a"}`
`{"type":"unsubscribeEquipment","equipmentId":"8f6c7bbd-3f1f-4b6f-9bcb-84db99411f2a"}`
`{"type":"ping"}`

Postman notes:
- Collection file: `postman/websockets-api.postman_collection.json`
- Connect manually to `ws://localhost:8085/ws/reservations` and send the payloads from this README.
