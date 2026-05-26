# Security Considerations

## SQL Injection

The API uses Spring Data JPA repositories for database access instead of building SQL strings manually. JPA/Hibernate uses parameter binding and prepared statements under the hood, so request values are passed as parameters rather than concatenated into SQL queries. This prevents typical SQL injection attacks.

## XSS

The gRPC API returns protobuf messages and does not render user-provided content into HTML pages. Since there is no browser UI in this API, XSS is not directly applicable in the same way as it would be for a web frontend.

## CSRF

CSRF is mainly a risk for browser-based applications that use cookies or sessions for authentication. This gRPC API is intended for direct gRPC clients such as Postman, `grpcurl`, or service-to-service calls and does not use browser session authentication.

## Secrets

Database credentials are provided at runtime through environment variables. The Docker image does not contain secrets, and the local `.env` file must not be committed to Git. The committed `dotenv-template` file documents which variables are required without containing real secrets.

## Faults and Exceptions

Domain errors are handled as gRPC status codes or stream-level error messages instead of leaking Java stack traces to the client. Exceptions such as missing equipment, missing reservations, and invalid request values are mapped to `INVALID_ARGUMENT`, `NOT_FOUND`, or stream `ErrorInfo` responses with clear messages.

## UUID Identifiers

Reservations and equipment use UUID identifiers instead of sequential numeric IDs. UUIDs are not practically guessable in normal use, which reduces the risk of clients discovering resources by simply incrementing IDs.

## Authentication and Authorization

Authentication and authorization are outside the scope of this gRPC API slice. If they were required, they would normally be handled with gRPC interceptors and transport security such as TLS and token validation before returning or changing protected resources.
