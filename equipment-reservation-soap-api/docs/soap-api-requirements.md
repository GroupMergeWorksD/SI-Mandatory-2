# SOAP API Requirements

This document condenses the assignment requirements that apply to the SOAP API in this project.

## General Requirements

- The API must use database-stored information.
- The API must protect against common security issues where applicable, including SQL injection, IDOR, and XSS.
- A Postman collection or similar test suite must be provided.
- The test suite must include both positive and negative requests.
- Source code and test collections must be part of the delivery.

## SOAP-Specific Requirements

- The SOAP API must expose a generated WSDL.
- The SOAP API must define and implement at least 4 operations.
- At least 2 operations must read data.
- At least 2 operations must change data.
- The SOAP API must include at least 2 faults for error handling.
- SOAP requests and responses should be contract-first and generated from XSD.

## Project Scope

- Business domain: equipment reservation.
- WSDL URL: `/ws/equipment-reservation-soap-api.wsdl`.
- SOAP endpoint URL: `/ws`.
- Database: MySQL database `equipment_reservation_db`.

## SOAP Operations

- `create-reservation`: create a reservation for equipment.
- `get-reservation`: read one reservation by ID.
- `list-reservations`: read multiple reservations.
- `delete-reservation`: cancel a reservation (hard delete).

## SOAP Faults

- `equipment-not-found`: returned when creating a reservation for missing equipment.
- `reservation-not-found`: returned when reading, deleting, or cancelling a missing reservation.
- `reservation-conflict`: returned when a reservation cannot be created because of a domain conflict.

## Testing Expectations

- WSDL can be opened in a browser.
- Each SOAP operation has a Postman request.
- Fault cases have negative Postman requests.
- Database seed data supports repeatable testing.
