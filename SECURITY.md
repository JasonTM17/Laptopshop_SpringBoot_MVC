# Security Policy

This project is a portfolio application, but it follows production-minded security practices where practical.

## Supported Version

Security fixes are applied to the default branch.

## Reporting

Please do not open a public issue for sensitive findings. Contact the maintainer privately, or create a private advisory if the repository is hosted on GitHub.

Include:

- Affected route or component.
- Steps to reproduce.
- Expected impact.
- Suggested mitigation, if available.

## Baseline Practices

- Secrets must be provided by environment variables.
- Production should run with `SPRING_PROFILES_ACTIVE=prod`.
- Demo seed data should stay disabled outside the `local` profile.
- Admin routes require the `ADMIN` role.
- API authentication failures return JSON `401` responses instead of HTML login redirects.
- Security headers are enabled for storefront and admin pages, including CSP, referrer policy, permissions policy, frame protection, and `nosniff`.
- Public registration and password-change flows require a strong password pattern.
- Image uploads are restricted by MIME type, extension, size, sanitized filename, and file signature.
- Docker runtime uses a non-root user.
- CI includes Maven tests/package, Docker image build, Dependabot, and CodeQL Java analysis.
