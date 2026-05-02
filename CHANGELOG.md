# Changelog

All notable changes to Laptopshop are documented here. Detailed release context lives in [RELEASE_NOTES.md](RELEASE_NOTES.md).

## Unreleased

- Hardened Spring Security route fallback, login failure handling, security headers, API JSON entry points, image upload validation, and remember-me behavior.
- Added regression coverage for security headers, strong password policy, and spoofed image upload rejection.
- Added CodeQL Java analysis workflow and documented the updated security baseline.
- Hardened the Docker runtime to run as a non-root user.
- Corrected portfolio container publishing docs/workflow to use Docker Hub `nguyenson1710/laptopshop-spring-boot-mvc` as the primary image.
- Account-synced wishlist, real payment integration, promotion/spec schema, object storage, and notification adapters are planned as possible next-release slices.

## v1.0.0 Portfolio Showcase - 2026-04-30

### Added

- Retail-style storefront with red Laptopshop design system, dense product cards, category navigation, promo sections, search autocomplete, wishlist feedback, and buy-now shortcut.
- Professional About page with service story, operating metrics, release status, reviewer checklist, warranty, return, terms, and privacy sections.
- Customer flows for account, password update, cart, checkout, order history, stock validation, and transactional order creation.
- Admin dashboard with real revenue/factory/low-stock/recent-activity metrics, backend search/filter for product/order/user lists, order status validation, and CSV order export.
- Local demo bootstrap for reviewer-ready roles, accounts, sample products, sample cart, and sample order history.
- Production metadata and assets: favicon/app manifest, sitemap, robots, Open Graph-ready cover, cacheable static assets, and health endpoints.
- Documentation set: README, installation guide, project About, feature matrix, reviewer guide, architecture, testing, deployment, showcase script, portfolio checklist, contributing, security, and release notes.
- GitHub Actions CI, Docker build, Docker Compose setup, Render blueprint, `.env.example`, `.editorconfig`, and `.gitattributes`.

### Changed

- Reworked storefront and admin UI into a cohesive Laptopshop retail experience.
- Moved configuration toward local/prod profiles and environment variables instead of committed secrets.
- Replaced random dashboard chart data with backend-driven metrics.
- Standardized DTO validation for password, cart, checkout, and product filtering behavior.
- Improved API behavior so unauthenticated/forbidden API calls return JSON errors.

### Fixed

- Corrected route/link polish for password flows, register policy links, admin quick search, product pagination/filter query strings, and static metadata.
- Hardened cart quantity handling, checkout empty-cart behavior, stock updates, and missing resource handling.
- Removed legacy/demo static assets and dead UI patterns where they could mislead reviewers.

### Verified

- `.\mvnw.cmd test`
- `.\mvnw.cmd package`
- Browser smoke for storefront, About, customer buy-now/checkout, admin dashboard/search, autocomplete, wishlist, sitemap, and robots metadata.
