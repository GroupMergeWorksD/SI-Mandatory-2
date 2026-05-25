# Security Considerations

## SQL Injection

The API uses Spring Data JPA repositories for database access instead of building SQL strings manually. JPA/Hibernate uses parameter binding and prepared statements under the hood, so request values are passed as parameters rather than concatenated into SQL queries. This prevents typical SQL injection attacks.

## XSS

The SOAP API returns XML responses and does not render user-provided content into HTML pages. SOAP/JAXB serialization escapes XML content, so values are returned as data rather than executable markup. Since there is no browser UI in this API, XSS is not directly applicable in the same way as it would be for a web frontend.

## CSRF

CSRF is mainly a risk for browser-based applications that use cookies or sessions for authentication. This SOAP API is intended for direct SOAP clients such as Postman or service-to-service calls and does not use browser session authentication. If cookie-based browser authentication were added later, CSRF protection would need to be enabled.

## Secrets

Database credentials are provided at runtime through environment variables. The Docker image does not contain secrets, and the local `.env` file must not be committed to Git. The committed `dotenv-template` file documents which variables are required without containing real secrets.

## Faults and Exceptions

Domain errors are handled as SOAP faults instead of leaking Java stack traces to the client. Exceptions such as missing equipment, missing reservations, invalid reservation times, equipment unavailability, and reservation conflicts are mapped to SOAP client faults with clear fault strings.

## Authentication and Authorization

Authentication and authorization are outside the scope of this SOAP API slice. If they were required, they would normally be handled with SOAP-oriented security mechanisms such as WS-Security for credentials, signatures, and message-level security, combined with application-level authorization checks before returning or changing protected resources.
