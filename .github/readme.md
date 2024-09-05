![UIT](https://img.shields.io/badge/from-UIT%20VNUHCM-blue?style=for-the-badge&link=https%3A%2F%2Fwww.uit.edu.vn%2F)


YAMP - Yet Another Microservice Project
# Introduction
## Project Overview:
- This e-commerce cloud-based microservice project is designed for learning purposes, with a strong emphasis on microservices architecture and its associated concerns. Key areas of focus include:
    - Service Discovery
    - Load Balancing
    - Fault Tolerance
    - Distributed Tracing,Distributed transaction
    - Monitoring
    - ... and more

- Given my interest in DevOps, I’ve also integrated several DevOps practices into the project, such as:
  CI/CD Pipeline
  Containerization
  Orchestration
  Monitoring
  Logging
  also
- I write about Unit test and integration test for integrated with CI/CD pipeline

While the project is still evolving and currently lacks some business logic and features, I am committed to continuously adding more functionalities in the future.

# Architecture

# Tech stack
## **Java Backend**: Spring Framework
- **Spring Boot**:
- **Spring Cloud**: Spring Cloud gateway
- **Spring Security**: Spring Resource Server, Spring Authorization Server
- **Spring Data JPA**: Spring Data Redis, Spring Data PostgresSQL
- **Cache**: Redis
- **Database**: PostgresSQL
- [x] **Distributed session management**: Spring Session and Redis
- [x] **Distributed caching**: Redis

## **Monitoring and Observability**: Using Spring Boot Actuator, Prometheus and Grafana
- [x] **Metrics**: Using Micrometer
- [x] **Distributed tracing**: OpenTelemetry,Tempo and Grafana
- **I'm current working on these features**
    - [ ] **Distributed logging aggregation**: Loki and Alloy
    - [ ]  **Visualization all these metrics**: Grafana


- [x] **Authentication and Authorization**: Using Spring Security, OAuth2, Jwt
# Devops tools
- **CI/CD**:
    - CI: Github Actions
    - CD: ArgoCD
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **Delivery tool for deploying application on Kubernetes**: ArgoCD,Helm chart
- **Logging Aggregation**: Loki
- **Monitoring**: Prometheus
- **Visualization**: Grafana

For more information about the Pipeline, please refer to the this [readme.md](../../.github/workflows/readme.md)

For more information about how I deploy this project, please refer to the [deployment repository](https://github.com/ngodat0103/yamp-deployment.git)

