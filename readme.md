![UIT](https://img.shields.io/badge/from-UIT%20VNUHCM-blue?style=for-the-badge&link=https%3A%2F%2Fwww.uit.edu.vn%2F)
# YAMP - Yet Another Microservice Project
## Introduction
An E-Commerce cloud based microservice project for learning purpose. This project is built with the following technologies:
- **Java Backend**: Spring Framework 
- **Cache**: Redis
- **Database**: PostgresSQL
## Architecture

## Features
For more information about each service, please refer to the README.md in each service folder.
- [x] **Distributed session management**: Using Spring Session and Redis
- [x] **Distributed caching**: Using Redis
- [ ] **Distributed logging**: Using Loki and Grafana
- [ ] **Monitoring and Observability**: Using Spring Boot Actuator, Prometheus and Grafana 
- [x] **Authentication and Authorization**: Using Spring Security, OAuth2, JWT at Gateway
## Devops tools
For more information about the Pipeline, please refer to the README.md in the .github/workflows folder.
- **CI/CD**: Github Actions
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **Logging**: Loki
- **Monitoring**: Prometheus
- **Visualization**: Grafana
## How to run this project locally
### Prerequisites
- Docker
- Docker Compose
### Steps
1. Clone the project
2. Create a .env file in the root folder of the project with the following content:
```VERSION={version}``` with {version} is the version of the project, you can check at github packages, example: VERSION=pr-26
3. Run the following command:
```docker compose up```

