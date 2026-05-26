
### Running application in Docker
Commands for building the image and running the container.

Open a terminal in the folder `grpc-api` and run the following commands:
1. #### Build image: `docker build -t grpc-api-image .`
2. #### Run container: `docker run --rm --name grpc-api -p 8084:8084 -e GRPC_MYSQL_ROOT_USER=root -e GRPC_MYSQL_ROOT_PASSWORD=yourpassword -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/equipment_reservation_db grpc-api-image`

The gRPC server listens on `localhost:8084`. A MySQL database must already be running and reachable at the URL above.

### Running with Docker Compose (this module)
From the `grpc-api` folder, copy `dotenv-template` to `.env` if you want a custom MySQL password, then run:

```bash
docker compose up -d
```

This starts a dedicated MySQL container (`grpc-mysql` on host port `3308`) and the gRPC API on port `8084`.

To rebuild and restart:

```bash
docker compose down -v
docker compose up --build -d
```

### Running locally with Maven
Requires **JDK 24** and **Maven**. MySQL must be running with database `equipment_reservation_db` (default URL: `localhost:3306`).

Open a terminal in the folder `grpc-api`, set credentials, and start the app:

```bash
set GRPC_MYSQL_ROOT_USER=root
set GRPC_MYSQL_ROOT_PASSWORD=yourpassword
mvn spring-boot:run
```

On Linux/macOS, use `export` instead of `set`.

### Testing the API
- **Proto file:** `src/main/proto/equipment_reservation_service.proto`
- **Postman:** import the proto file and use `postman/grpc-api.postman_collection.json`
- **grpcurl** (example — reservation id from seed data):

```bash
grpcurl -plaintext -d "{\"reservation_id\":\"2d8f4162-2b85-48d1-8b59-d2be0a6e512a\"}" localhost:8084 equipmentreservation.EquipmentReservationService/GetReservation
```
