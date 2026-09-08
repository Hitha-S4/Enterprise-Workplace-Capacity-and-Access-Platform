# NexusSpace

## Enterprise Workplace Capacity and Access Platform

**Winning Team of the IBM Developer Academy Program**

NexusSpace is a cloud-native workplace operations platform for coordinating shared capacity across corporate sites. It began as a cafeteria reservation workflow and evolved into a multi-location resource orchestration system: employees reserve time-bound worksite capacity, managers approve exceptions, and administrators monitor utilization across the organization.

This reflects a real operational problem in hybrid workplaces: fixed resources must be allocated fairly, access must be auditable, and facilities teams need a single view of demand across locations instead of disconnected spreadsheets and queues.

## What It Solves

- **Multi-site capacity allocation:** Model locations, workplace hubs, seat inventories, and time slots in one system.
- **Role-aware operations:** Employees create reservations, managers review approval queues, and administrators monitor the full portfolio.
- **Conflict-aware booking:** Check seat availability for a specific location, resource, date, and time window before confirming a reservation.
- **Operational visibility:** Track active reservations, pending approvals, cancellations, and capacity utilization.
- **Enterprise access:** Integrate authenticated users and role-based workflows with W3 ID-compatible identity flows.

## Technical Highlights

- Spring Boot REST services with separated controller, service, repository, DTO, and mapper layers.
- Domain-level booking invariants for past dates, unavailable seats, invalid resource relationships, and duplicate time-slot reservations.
- Bean Validation on API requests with structured Problem Details responses for validation, missing-resource, and conflict errors.
- PostgreSQL persistence for users, locations, workplace hubs, seats, and reservations.
- React and TypeScript operations console with role-specific dashboards.
- Swagger/OpenAPI-ready REST endpoints for facilities, identity, and reporting integrations.
- Mockito-backed service tests for reservation conflict behavior.
- Docker Compose deployment for the application stack, database, and reverse proxy.

## Architecture

```text
React + TypeScript console
		  |
		 REST API
		  |
Spring Boot domain services
		  |
		PostgreSQL
```

The current resource type is cafeteria seating, but the domain boundary is intentionally designed around locations, resources, capacity, approvals, and reservations. That makes the platform extensible to collaboration zones, visitor seating, parking, or other shared workplace infrastructure without changing the core allocation model.

## Tech Stack

- **Frontend:** React, TypeScript, Vite, Tailwind CSS, shadcn/ui
- **Backend:** Java, Spring Boot, Maven
- **Data:** PostgreSQL, JPA/Hibernate
- **Security:** Authenticated, role-aware workflows with W3 ID integration points
- **Delivery:** Docker Compose, Caddy, Nginx

## Local Development

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Full stack

```bash
docker compose up --build
```

## Resume Positioning

**NexusSpace | Enterprise Workplace Capacity and Access Platform**

Built a role-aware, multi-location reservation platform using React/TypeScript, Spring Boot, and PostgreSQL; enforced booking invariants, duplicate-reservation detection, manager approval workflows, structured REST error contracts, and administrator utilization dashboards for shared workplace capacity.

