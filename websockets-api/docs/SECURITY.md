# WebSocket API Security Notes

This API uses Spring Data JPA repositories with parameterized SQL generation, which helps mitigate SQL injection by default.

## SQL injection

- Avoids string-concatenated SQL queries.
- Uses repository methods and entity mappings.

## XSS

- This API returns JSON only.
- No server-rendered HTML templates are used.

## CSRF

- Browser-cookie session auth is not used for this API.
- Communication is done through explicit WebSocket messages and backend validation.

## Input validation

- UUID values are validated before processing:
    - `reservationId`
    - `equipmentId`
