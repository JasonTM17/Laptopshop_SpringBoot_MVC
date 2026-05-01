# Feature Matrix

This matrix maps visible product features to implementation areas and verification notes. It is useful for reviewers who want to understand whether the app is only UI polish or a real full-stack slice.

## Storefront

| Feature | Route / Asset | Implementation | Verification |
| --- | --- | --- | --- |
| Retail home page | `/` | `HomePageController`, `homepage/show.jsp`, `banner.jsp`, `client.css` | Browser smoke checks header, hero, product cards |
| Search autocomplete | Header search | `ProductApiController`, `ProductSuggestionDTO`, `ui.js` | API test and browser suggestion smoke |
| Catalog filters | `/products` | `ItemController`, `ProductCriteriaDTO`, `ProductSpecs`, `ProductService` | Product service tests and browser catalog smoke |
| Product card wishlist | Product grids | `product-card.jsp`, `ui.js`, localStorage | Browser-local interaction smoke |
| Product detail | `/product/{id}` | `ItemController`, `detail.jsp`, Product JSON-LD view data | MockMvc/product route and browser smoke |
| Buy-now | Product detail | `main.js`, `CartAPI`, `ProductService` | Browser customer flow redirects to checkout |
| About page | `/about` | `HomePageController`, `about/show.jsp`, `client.css` | Browser smoke and screenshot-ready page |

## Customer Account And Orders

| Feature | Route / Asset | Implementation | Verification |
| --- | --- | --- | --- |
| Login/register | `/login`, `/register` | Spring Security, `RegisterValidator`, auth JSPs | Validator tests and route smoke |
| Profile and password | `/account` | `AccountController`, `ChangePasswordDTO`, `UserService` | Controller and validation coverage |
| Cart management | `/cart`, `/api/add-product-to-cart` | `CartAPI`, `CartRequestDTO`, `CartResponseDTO`, `ProductService` | Cart tests and browser smoke |
| Checkout | `/checkout`, `/place-order` | `CheckoutDTO`, `OrderService`, transaction boundaries | Service/controller tests |
| Order history | `/order-history` | `ItemController`, `OrderService` | Authenticated E2E smoke |

## Admin

| Feature | Route / Asset | Implementation | Verification |
| --- | --- | --- | --- |
| Dashboard metrics | `/admin` | `DashboardService`, dashboard DTOs, Chart.js view data | Admin tests and browser dashboard smoke |
| Product management | `/admin/product` | `ProductController`, `ProductService`, upload validation | Admin filter tests and manual CRUD-ready UI |
| Order management | `/admin/order` | `OrderController`, `OrderStatus`, `OrderService` | Status validation tests |
| User management | `/admin/user` | `UserController`, `UserService` | Admin user search/filter tests |
| CSV export | `/admin/report/orders.csv` | `AdminReportController` | MockMvc export test and browser link smoke |
| Quick search | Admin topbar | `admin/layout/header.jsp`, product search route | Browser admin quick-search smoke |

## Production And Portfolio

| Feature | File / Route | Implementation | Verification |
| --- | --- | --- | --- |
| Profiles and env vars | `application-local.properties`, `application-prod.properties`, `.env.example` | Environment-driven configuration | README and deployment docs |
| Demo seed | `LocalDemoDataInitializer` | Local-only reviewer data | Seed data test |
| Health checks | `/actuator/health`, `/actuator/info` | Spring Boot Actuator | Test/manual smoke |
| Static metadata | `/robots.txt`, `/sitemap.xml`, `/site.webmanifest` | `WebMvcConfig`, webapp resources | Static route smoke |
| Docker | `Dockerfile`, `docker-compose.yml` | WAR package and MySQL service | Docker docs/checklist |
| CI | `.github/workflows/ci.yml` | Maven package workflow | GitHub Actions |
| Security scanning | `.github/workflows/codeql.yml`, `.github/dependabot.yml` | CodeQL Java analysis and dependency update checks | GitHub Security |
| Runtime hardening | `SecurityConfiguration`, `UploadService`, `Dockerfile`, `SECURITY.md` | Security headers, strict route fallback, upload signature checks, non-root container user | Automated tests and Docker smoke |

## Known Intentional Gaps

| Area | Current release | Next release path |
| --- | --- | --- |
| Payment | UI choice only | Payment provider adapter and payment status model |
| Wishlist | Browser-local | Account-synced wishlist table and page |
| Promotions/specs | View-layer derivation | Dedicated promotion/spec schema |
| Notifications | Not sent | Email/SMS adapter for order updates |
| Media storage | Local upload path | Object storage/CDN adapter |
