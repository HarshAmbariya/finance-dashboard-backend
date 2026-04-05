# Finance Dashboard Backend

A RESTful backend API for a role-based finance dashboard system. Built with **Java 17**, **Spring Boot**, **Spring Security**, **Spring Data JPA**, **PostgreSQL**, **JWT authentication**, and **Bucket4j** for rate limiting.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot |
| Security | Spring Security + JWT |
| ORM | Spring Data JPA (Hibernate) |
| Database | PostgreSQL |
| Rate Limiting | Bucket4j |
| Build Tool | Maven |

---

## Features

- **User & Role Management** — Create users, assign roles (ADMIN, ANALYST, VIEWER), manage active/inactive status
- **Financial Records** — Full CRUD for income/expense entries with category, date, and notes
- **Dashboard Summary APIs** — Aggregated data: total income, total expenses, net balance, category-wise totals, monthly trends
- **Role-Based Access Control** — Enforced via Spring Security at the endpoint level
- **JWT Authentication** — Stateless token-based auth with secure endpoints
- **Input Validation & Error Handling** — Bean validation with meaningful error responses and proper HTTP status codes
- **Rate Limiting** — Per-user request throttling via Bucket4j
- **Pagination & Filtering** — Filter records by date range, category, and type; paginated responses

---

## Roles & Permissions

| Action | VIEWER | ANALYST | ADMIN |
|---|:---:|:---:|:---:|
| View dashboard summary | ✅ | ✅ | ✅ |
| View financial records | ✅ | ✅ | ✅ |
| Filter & search records | ✅ | ✅ | ✅ |
| Access analytics/insights | ❌ | ✅ | ✅ |
| Create financial records | ❌ | ❌ | ✅ |
| Update financial records | ❌ | ❌ | ✅ |
| Delete financial records | ❌ | ❌ | ✅ |
| Manage users | ❌ | ❌ | ✅ |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### 1. Clone the Repository

```bash
git clone https://github.com/HarshAmbariya/finance-dashboard-backend.git
cd finance-dashboard-backend
```

### 2. Configure the Database

Create a PostgreSQL database:

```sql
CREATE DATABASE finance_dashboard;
```

Update `src/main/resources/application.properties` (or `application.yml`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_dashboard
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## API Overview

### Authentication

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Login and receive JWT | Public |

### Users

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/users` | List all users | ADMIN |
| GET | `/api/users/{id}` | Get user by ID | ADMIN |
| PUT | `/api/users/{id}` | Update user | ADMIN |
| PATCH | `/api/users/{id}/status` | Activate/deactivate user | ADMIN |
| DELETE | `/api/users/{id}` | Delete user | ADMIN |

### Financial Records

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/records` | List records (with filters & pagination) | ALL |
| GET | `/api/records/{id}` | Get record by ID | ALL |
| POST | `/api/records` | Create a new record | ADMIN |
| PUT | `/api/records/{id}` | Update a record | ADMIN |
| DELETE | `/api/records/{id}` | Delete a record | ADMIN |

**Filter Parameters** (query params on `GET /api/records`):

| Param | Type | Example |
|---|---|---|
| `type` | String | `INCOME` or `EXPENSE` |
| `category` | String | `Salary`, `Rent` |
| `from` | Date | `2024-01-01` |
| `to` | Date | `2024-03-31` |
| `page` | Integer | `0` |
| `size` | Integer | `10` |

### Dashboard Summary

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/dashboard/summary` | Total income, expenses, net balance | ALL |
| GET | `/api/dashboard/by-category` | Totals grouped by category | ANALYST, ADMIN |
| GET | `/api/dashboard/monthly-trends` | Monthly income vs expense trend | ANALYST, ADMIN |
| GET | `/api/dashboard/recent` | Recent financial activity | ALL |

---

## Data Model

### User

```
id, name, email, password (hashed), role (ADMIN/ANALYST/VIEWER), status (ACTIVE/INACTIVE), createdAt
```

### Financial Record

```
id, amount, type (INCOME/EXPENSE), category, date, notes, createdBy (userId), createdAt, updatedAt
```

---

## Assumptions Made

- A user can only have one role at a time.
- Soft delete is not implemented; records are hard-deleted.
- JWT tokens are not blacklisted on logout (stateless design).
- The `ANALYST` role can read all records but cannot modify them.
- All monetary values are stored as `BigDecimal` to avoid floating-point issues.

---

## Project Structure

```
src/
├── main/
│   ├── java/com/finance/dashboard/
│   │   ├── auth/          # JWT utilities, filters, auth controller
│   │   ├── config/        # Security config, rate limiter config
│   │   ├── controller/    # REST controllers
│   │   ├── dto/           # Request/Response DTOs
│   │   ├── exception/     # Global exception handler
│   │   ├── model/         # JPA entities
│   │   ├── repository/    # Spring Data repositories
│   │   └── service/       # Business logic
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/finance/dashboard/
```

---

## Error Handling

All errors return a consistent JSON structure:

```json
{
  "timestamp": "2024-04-06T10:00:00",
  "status": 403,
  "error": "Forbidden",
  "message": "You do not have permission to perform this action."
}
```

Common status codes used: `200`, `201`, `400`, `401`, `403`, `404`, `429` (rate limit), `500`.

---

## Author

**Harsh Ambariya**
[GitHub](https://github.com/HarshAmbariya)
