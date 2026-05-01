# Deployment Guide

Laptopshop is deployment-ready as a Dockerized Spring Boot WAR. The repository includes a `Dockerfile`, `docker-compose.yml`, `render.yaml`, health checks, and profile-based configuration.

## Deployment Targets

| Target | Use case |
| --- | --- |
| Local Maven | Fast development on a machine with MySQL |
| Docker Compose | Reviewer demo with app + MySQL together |
| Render Docker service | Public portfolio deployment |
| Any Docker host | VPS, Railway-like platform, Fly.io-like platform, or internal server |

## Production Environment Variables

Set these variables in the hosting platform. Do not commit real values.

| Variable | Required | Example |
| --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | Yes | `prod` |
| `PORT` | Yes | `8080` |
| `SPRING_DATASOURCE_URL` | Yes | `jdbc:mysql://host:3306/laptopshop?useSSL=true&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8` |
| `SPRING_DATASOURCE_USERNAME` | Yes | `laptopshop_app` |
| `SPRING_DATASOURCE_PASSWORD` | Yes | protected dashboard value |
| `JPA_DDL_AUTO` | Recommended | `validate` |
| `SPRING_SESSION_JDBC_INITIALIZE_SCHEMA` | Recommended | `never` |
| `MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE` | Optional | `health,info` |
| `APP_BASE_URL` | Recommended | live public base URL for sitemap/robots |
| `JAVA_OPTS` | Optional | `-Xms256m -Xmx512m` |

Use an external MySQL-compatible database for production. The local demo seed only runs under the `local` profile.

## Local Release Build

```powershell
.\mvnw.cmd package
docker build -t laptopshop:release .
```

Published portfolio image:

```powershell
docker pull ghcr.io/jasontm17/laptopshop-spring-boot-mvc:latest
```

Run the image with environment variables:

```powershell
docker run --rm -p 8080:8080 `
  -e SPRING_PROFILES_ACTIVE=prod `
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host:3306/laptopshop?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh&characterEncoding=UTF-8" `
  -e SPRING_DATASOURCE_USERNAME="laptopshop_app" `
  -e SPRING_DATASOURCE_PASSWORD="replace_me" `
  -e JPA_DDL_AUTO=validate `
  -e SPRING_SESSION_JDBC_INITIALIZE_SCHEMA=never `
  -e APP_BASE_URL="https://your-live-demo.example" `
  laptopshop:release
```

Smoke:

```powershell
curl.exe http://localhost:8080/actuator/health
curl.exe http://localhost:8080/actuator/info
```

## Render Deployment

`render.yaml` creates a Docker web service shell and leaves database credentials as protected values.

1. Push the repository to GitHub.
2. Create or connect a MySQL-compatible database.
3. In Render, create a Blueprint from `render.yaml` or create a Docker Web Service manually.
4. Set the required environment variables from the table above.
5. Keep `SPRING_PROFILES_ACTIVE=prod`.
6. Set the health check path to `/actuator/health`.
7. Deploy.

After deploy, test:

```text
/actuator/health
/
/products
/product/1
/login
/about
```

Then log in with whichever production/demo accounts you intentionally created for that environment.

## Database Notes

For production, prefer:

- A dedicated database user instead of root.
- `JPA_DDL_AUTO=validate` after schema is created.
- Regular backups.
- No local demo seed unless the environment is explicitly a public demo.

For a public demo database, create sanitized demo accounts only. Never reuse personal credentials.

## Troubleshooting

| Symptom | Check |
| --- | --- |
| App starts then exits | Missing datasource URL, username, or password |
| Health check fails | Confirm `PORT`, `/actuator/health`, and database connectivity |
| Login fails after restart | Verify Spring Session table exists and session schema setting |
| Static assets missing | Confirm the app is deployed from the packaged WAR/Docker image |
| 500 on startup with prod profile | Check `JPA_DDL_AUTO=validate` against the actual schema |

## Release Checklist

```powershell
.\mvnw.cmd package
docker build -t laptopshop:release .
docker compose config
docker pull ghcr.io/jasontm17/laptopshop-spring-boot-mvc:latest
```

Manual smoke:

```text
/ -> /products -> /product/1 -> /login -> /about -> /actuator/health
```
