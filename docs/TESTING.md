# Testing Guide

Laptopshop includes automated tests for business rules, controller behavior, security entry points, and portfolio smoke flows.

## Commands

Run tests:

```powershell
.\mvnw.cmd test
```

Build the WAR and run all tests:

```powershell
.\mvnw.cmd package
```

Build the Docker image:

```powershell
docker build -t laptopshop:local .
```

Pull the published portfolio image after the GHCR workflow runs:

```powershell
docker pull ghcr.io/jasontm17/laptopshop-spring-boot-mvc:latest
```

Validate Docker Compose syntax:

```powershell
docker compose config
```

## Current Coverage

| Area | What is covered |
| --- | --- |
| Validation | Register form, password strength, null-safe checks |
| Product catalog | Filtering, search by name, product suggestions, sorting, price ranges |
| Cart | Add, update, remove, invalid quantity, stock limits |
| Checkout | Empty cart, order creation, stock decrement, sold count |
| Security | API JSON `401/403`, protected page redirect, admin access denial |
| E2E smoke | Home, catalog, product detail, manifest, favicon, sitemap, health |
| Auth flow | Customer add-to-cart, cart page, checkout, order history |
| Admin | Dashboard metrics, backend list search/filter, order detail route, CSV export, status transition validation |

## Manual Smoke Checklist

Use the demo accounts from the README.

```text
/ -> /products -> /products?factory=APPLE -> /product/1
/login -> /cart -> /checkout -> /thanks -> /order-history
/admin -> /admin/product -> /admin/order -> /admin/user
/about -> /actuator/health
```

Check:

- No console errors.
- Header/search/cart badge behave on desktop and mobile.
- Search autocomplete opens, links to product detail, and closes without console errors.
- Product detail buy-now adds the selected quantity and routes to checkout.
- Product cards do not overflow.
- Wishlist heart persists in localStorage and shows clear browser-local feedback.
- Cart quantity cannot become zero or negative.
- Checkout does not create an order from an empty cart.
- Admin tables remain readable on small screens.
- Admin product, order, and user search/filter keep state across pagination.
- Admin dashboard chart uses backend data, not random values; CSV export downloads order data.
- About page renders the release status, reviewer checklist, service policies, terms, and privacy anchors.
- Release docs are linked from README and installation guide.

## Release Smoke Evidence

For the v1.0.1 portfolio release, the final manual/browser smoke covered:

```text
/ -> /products?factory=APPLE -> /product/1 -> /cart -> /checkout
/login -> /register -> /about -> /admin
```

Interaction checks:

- Search autocomplete shows product suggestions.
- Wishlist toggle updates local state and feedback.
- Category drawer opens and closes.
- Customer login works with the seeded demo account.
- Product detail buy-now routes to checkout.
- Admin dashboard charts load with backend data.
- Admin quick search routes to `/admin/product?q=...`.
- `/api/products/suggest?name=mac`, `/sitemap.xml`, and `/robots.txt` return `200`.

## When To Add Tests

Add or update tests when changing:

- Cart or order business rules.
- Product filtering or pagination.
- Authentication, authorization, or redirect behavior.
- DTO validation.
- Admin routes that load IDs from the URL.
