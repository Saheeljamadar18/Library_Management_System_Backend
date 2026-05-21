# Library Management System (Spring Boot)

REST API for students, authors, books, library cards, and borrowing transactions.

## Run with Docker (recommended)

**Requirements:** Docker Desktop or Docker Engine + Docker Compose v2

```bash
cd Library_Management_System
cp .env.example .env   # optional — defaults work for local dev
docker compose up --build
```

| Service | URL |
|---------|-----|
| API | http://localhost:7777 |
| Swagger UI | http://localhost:7777/swagger-ui.html |
| MySQL | `localhost:3306` (database `student_lib`, user `root`) |

Stop containers:

```bash
docker compose down
```

Remove database volume (fresh DB):

```bash
docker compose down -v
```

### Environment variables

| Variable | Default | Description |
|----------|---------|-------------|
| `MYSQL_ROOT_PASSWORD` | `libroot` | MySQL root password (also used by the API) |
| `MYSQL_PORT` | `3306` | Host port for MySQL |
| `API_PORT` | `7777` | Host port for the Spring Boot API |

Inside Docker, the API connects to MySQL at hostname `mysql` (not `localhost`).

## Run locally (without Docker)

1. Start MySQL with database `student_lib`
2. Copy `src/main/resources/application-local.properties.example` → `application-local.properties` and set credentials
3. Run:

```bash
# Requires Java 21 (class file version 65)
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
./mvnw spring-boot:run
```

Verify API: `curl http://localhost:7777/dashboard/apis/stats`

## Frontend

The Next.js app in `../student_lib_frontend` expects the API at `http://localhost:7777` (same port when using Docker).

```bash
cd ../student_lib_frontend
npm run dev
```
