# bed-master

A Spring Boot 4.0.4 application built with Java 21, designed as a bed management system (BED-MASTER).

## Technology Stack

- **Framework**: Spring Boot 4.0.4
- **Language**: Java 21
- **Build Tool**: Maven
- **Database**: MySQL (via mysql-connector-j)

## Project Dependencies

### Core Spring Boot Starters
- **spring-boot-starter-data-jpa** - JPA/Hibernate ORM support for database operations
- **spring-boot-starter-webmvc** - MVC web framework for building REST/web controllers
- **spring-boot-starter-restclient** - HTTP client for external REST API communication
- **spring-boot-starter-thymeleaf** - Template engine for server-side rendering
- **spring-boot-starter-validation** - Bean validation and constraint checking

### Additional Libraries
- **springdoc-openapi-starter-webmvc-ui** (v3.0.2) - Swagger/OpenAPI documentation UI
- **mysql-connector-j** - MySQL database driver
- **lombok** - Boilerplate code reduction (getters, setters, constructors)

### Testing Dependencies
Comprehensive test starters for Data JPA, RestClient, Thymeleaf, Validation, and WebMVC

## Build & Development

The project uses Maven with spring-boot-maven-plugin for building executable JAR files. Lombok annotation processing is configured in the Maven compiler plugin.

## Potential Areas for Development

- REST API endpoints for bed management
- Web interface using Thymeleaf templates
- Database models for bed, booking, and reservation management
- Integration with external services via RestClient
- API documentation via Swagger/OpenAPI UI
