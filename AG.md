
# Commerce API — Project Instructions

## 1. Project Overview

Commerce API is a production-oriented e-commerce and order-management
backend built as a modular monolith using Java and Spring Boot.

This is both:
- A practical backend engineering portfolio project.
- A long-term learning project for developing mid-level Java/Spring Boot
  backend engineering skills.

The project should demonstrate production-minded engineering rather
than simply implementing CRUD endpoints.

## 2. Main Engineering Goal

The developer's target is to become a mid-level Java/Spring Boot
backend engineer by mid-to-late 2027.

Use this project to develop practical understanding of:

- Spring Framework and Spring Boot internals.
- REST API design and HTTP semantics.
- PostgreSQL, SQL, JPA, Hibernate, and transaction management.
- Spring Security and authentication/authorization.
- Unit, integration, and database testing.
- Database performance and query optimization.
- Connection pooling and concurrency.
- Caching with Redis.
- Asynchronous processing and messaging.
- Error handling, resilience, and reliability.
- Logging, monitoring, and observability.
- Docker and production deployment practices.
- Backend architecture and system design.

Do not introduce technologies just to make the project look complex.
Every technology must solve a real problem or teach an important
backend engineering concept.

## 3. Technology Stack

Primary stack:

- Java 21
- Spring Boot
- Maven
- Spring MVC
- PostgreSQL
- Spring Data JPA
- Hibernate
- Flyway
- Jakarta Bean Validation
- Lombok
- MapStruct
- Spring Boot Actuator
- JUnit 5
- Mockito

Planned technologies to introduce when appropriate:

- Spring Security
- Redis
- RabbitMQ
- Testcontainers
- Docker Compose

Do not introduce all planned technologies at the beginning.
Add them incrementally when the architecture requires them.

Verify actual dependency versions and project configuration before
suggesting version-specific code.

## 4. Architecture

Use a modular monolith architecture.

Do not introduce microservices unless there is a strong engineering
reason and the developer explicitly agrees.

Use package-by-feature organization.

Base package:

com.psh.commerce

Main business modules:

- identity
- catalog
- inventory
- order
- payment
- notification

Shared infrastructure:

- common
- config
- exception

Each business module should organize its code into appropriate layers:

- controller
- service
- repository
- entity
- dto
- mapper

Avoid unnecessary abstractions and excessive layering.

Keep business logic out of controllers and repositories.

## 5. Business Requirements

The system will gradually support:

1. Customer registration and authentication.
2. Product and category management.
3. Inventory management.
4. Shopping cart management.
5. Order creation and order lifecycle management.
6. Payment simulation.
7. Notifications and background processing.
8. Administrative operations and reporting.

Payment integration is initially simulated.
Do not integrate a real payment provider without explicit approval.

Important domain rules:

- Order items must preserve the purchase-time product price.
- Product prices must not change historical order totals.
- Inventory must not become negative.
- Order creation and inventory reservation must be handled safely
  under concurrent requests.
- Business invariants must be enforced at the appropriate service
  and database layers.
- Database constraints must protect important data integrity rules.

## 6. Coding Standards

General:

- Prefer readable, maintainable, straightforward Java.
- Follow SOLID principles where they genuinely improve the design.
- Use constructor injection.
- Prefer immutable DTOs where appropriate.
- Use Java records for suitable request and response DTOs.
- Avoid unnecessary static utility classes.
- Avoid premature abstractions.
- Avoid duplicated business logic.

Spring:

- Keep controllers thin.
- Put business operations in services.
- Use repositories for persistence operations.
- Use @Transactional at appropriate service boundaries.
- Use @Valid and Jakarta Bean Validation for request validation.
- Use centralized exception handling with @RestControllerAdvice.
- Return appropriate HTTP status codes.
- Disable Open Session in View.
- Do not expose JPA entities directly through REST APIs.

JPA and database:

- Use Flyway for schema migrations.
- Use Hibernate ddl-auto=validate.
- Never use ddl-auto=create or create-drop as a normal development
  or production database strategy.
- Consider transaction boundaries carefully.
- Avoid N+1 query problems.
- Avoid unnecessary eager relationships.
- Use pagination for collection endpoints.
- Understand locking and concurrency before implementing inventory
  and order operations.
- Never assume @Transactional alone solves every concurrency problem.

Lombok:

- Prefer @RequiredArgsConstructor for constructor injection.
- Use @Getter and @Setter selectively.
- Avoid @Data on JPA entities.
- Avoid generated equals/hashCode implementations that cause
  problems with JPA relationships.

MapStruct:

- Prefer compile-time mapping.
- Keep mapping configuration explicit and understandable.
- Do not hide business logic inside mapper methods.
- Understand manual mapping when introducing a new mapping pattern.

Security:

- Never hardcode credentials, tokens, or secrets.
- Never commit production secrets.
- Do not store plaintext passwords.
- Enforce authorization on the backend.
- Validate user ownership when accessing user-specific resources.

## 7. Database and Local Development

PostgreSQL runs through Docker Compose.

The local Ubuntu PostgreSQL installation may already use port 5432.

The Commerce PostgreSQL container therefore uses:

- Host port: 5433
- Container port: 5432
- Database: commerce_db
- Username: commerce_user

The application connects using:

jdbc:postgresql://localhost:5433/commerce_db

Credentials should be configurable through environment variables:

- DB_URL
- DB_USERNAME
- DB_PASSWORD

Never assume Docker's initial database credentials have changed
just because compose environment variables were edited.
Existing PostgreSQL volumes preserve initialized database state.

Never delete Docker volumes or database data without explicit
confirmation from the developer.

## 8. Testing Requirements

Testing is part of implementation, not an optional final step.

Use:

- JUnit 5 for tests.
- Mockito for isolated unit tests.
- Spring Boot Test for integration tests.
- Testcontainers for realistic database integration tests when
  that stage is introduced.

Prioritize tests for:

- Business rules.
- Validation.
- Authentication and authorization.
- Transaction behavior.
- Inventory consistency.
- Order lifecycle.
- Concurrent operations.
- Error scenarios.

Do not claim a feature is verified unless relevant tests have
actually been executed.

## 9. AI Coding Agent Behavior

The developer is using this project to learn backend engineering.

When assisting:

- Understand the existing implementation before changing it.
- Explain important architectural decisions and trade-offs.
- Prefer minimal, focused changes over unnecessary rewrites.
- Do not generate an entire feature without explaining its design.
- Do not introduce dependencies without explaining why they are needed.
- Do not silently change the architecture.
- Do not invent existing classes, methods, configuration, or test results.
- Inspect actual files before suggesting changes to existing code.
- Preserve established naming and coding conventions.
- Identify security, data integrity, and concurrency risks.
- Point out when a solution is unsuitable for production.
- Distinguish a learning simplification from a production recommendation.

When debugging:

1. Read the complete relevant error and stack trace.
2. Identify the likely root cause.
3. Inspect relevant configuration and implementation.
4. Explain why the issue happens.
5. Suggest the smallest safe fix.
6. Run or recommend appropriate verification.
7. Mention any remaining risks.

Do not blindly change configuration until the root cause is understood.

## 10. Learning Approach

Teach concepts through this actual Commerce API project.

When introducing an important concept, explain:

1. What problem it solves.
2. Why the backend needs it.
3. How Spring or Java works internally when relevant.
4. How it applies to this project.
5. What trade-offs and alternatives exist.
6. How to test and verify the implementation.

Avoid unnecessary theoretical detours.

The developer prefers understanding the reasoning behind an
implementation rather than blindly copying code.

Do not overengineer features to demonstrate advanced knowledge.
Build incrementally, with a clear reason for each design decision.

## 11. Current Development Milestone

Current milestone: Project setup and Product Management.

Initial tasks:

- Configure the Spring Boot project.
- Start PostgreSQL through Docker Compose.
- Verify Spring Boot database connectivity.
- Configure Flyway.
- Implement Product creation.
- Implement Product retrieval.
- Implement paginated product listing.
- Implement Product updates and deletion.
- Add validation and global exception handling.
- Add meaningful tests.

Do not assume a task is completed until the code and configuration
confirm it.

## 12. Definition of Done

A feature is not complete merely because its endpoint works.

Before considering a feature complete, evaluate:

- Correct business behavior.
- Input validation.
- Appropriate HTTP responses.
- Error handling.
- Transaction boundaries.
- Database integrity.
- Security implications.
- Relevant automated tests.
- Query and performance implications.
- Maintainability.

Prioritize correctness, clarity, and production-minded engineering
over unnecessary complexity.
