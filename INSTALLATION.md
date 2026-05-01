# Laptopshop In 3 Minutes

This is the fastest reviewer flow for running Laptopshop locally.

## 1. Prepare

- JDK 17+
- MySQL 8+

Create an empty database named `laptopshop`.

## 2. Set local environment

```powershell
Copy-Item .env.example .env
$env:SPRING_PROFILES_ACTIVE = "local"
$env:MYSQL_HOST = "localhost"
$env:MYSQL_PORT = "3306"
$env:MYSQL_DATABASE = "laptopshop"
$env:MYSQL_USER = "root"
$env:MYSQL_PASSWORD = "your_mysql_password"
$env:APP_DEMO_SEED = "true"
$env:APP_BASE_URL = "http://localhost:8081"
```

`local` mode will auto-create:

- demo admin account
- demo customer account
- sample products
- sample order history
- sample cart

## 3. Run

With Maven:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

Open [http://localhost:8081](http://localhost:8081)

Or with Docker Compose:

```powershell
docker compose up --build
```

Open [http://localhost:8080](http://localhost:8080)

Published container image:

```powershell
docker pull ghcr.io/jasontm17/laptopshop-spring-boot-mvc:latest
```

## Demo Accounts

- Admin: `admin@laptopshop.dev` / `Admin@123`
- Customer: `customer@laptopshop.dev` / `Customer@123`

## What To Review

- Storefront: `/`
- Catalog: `/products`
- Product detail: `/product/1`
- Customer: `/cart`, `/order-history`, `/account`
- Admin: `/admin`
- Health check: `/actuator/health`

Full project setup and architecture notes live in [README.MD](README.MD).

More detail:

- [Release notes](RELEASE_NOTES.md)
- [Project About](docs/ABOUT.md)
- [Reviewer guide](docs/REVIEWER_GUIDE.md)
- [Feature matrix](docs/FEATURE_MATRIX.md)
- [GitHub repository setup](docs/GITHUB_REPO_SETUP.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Testing](docs/TESTING.md)
- [Deployment](docs/DEPLOYMENT.md)
