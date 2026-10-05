# Rideshare

Spring Boot microservices for ride requests, driver locations, and ride matching.

- `ride-service`: ride management (port 8083).
- `location-service`: driver locations backed by Redis (port 8082).
- `matching-service`: ride matching (port 8084).
- `docker-compose.yml`: local MySQL, Redis, Kafka, and ZooKeeper.

## Local configuration

Set the `MYSQL_ROOT_PASSWORD` environment variable before starting Docker Compose
and the ride service. Both must use the same password. For an existing MySQL data
volume, use its existing password; changing this variable does not reset it.

Docker Compose also supports a local `.env` file, which is ignored by Git. When
launching the ride service from an IDE, set `MYSQL_ROOT_PASSWORD` in its run
configuration as well; Spring Boot does not automatically load Compose's `.env`.

Start infrastructure with `docker compose up -d`, then run each service using its
Maven wrapper (`.\mvnw.cmd spring-boot:run` on Windows from its service folder).
