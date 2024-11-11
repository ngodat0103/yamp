# Overview
![Overview](../../draft/output/workflow.png)

I have create a CI/CD pipeline with doing the following:
- Build the project -> 
Run the unit test -> 
Run the integration test -> 
Build the docker image -> 
Push the docker image to the docker registry ->
Update the deployment repository with the new image tag ->
Deploy the application to the Kubernetes cluster
- Support caching, Security scanning, quality check
- Template for PR and Push

## WorkFlow push
```mermaid
graph TD;
    A[ci-pr-workflow] -->|Security Check| B[Security check using snyk]
    A -->|Build and Push| C[Build and push with pull request tag]
    C -->|Security Check| D[Security check for docker image]

    E[ci-push-workflow] -->|Quality Code Scan| F[Unit test and scan quality code]
    F -->|Vulnerability Scan| G[Check vulnerability using trivy]

    H[product-svc-workflow] -->|Push| I[product-svc-commit]
    H -->|PR Integration Test| J[product-svc-integration-test]
    J -->|PR| K[product-svc-pr]

    L[user-svc-workflow] -->|Push| M[user-svc-commit]
    L -->|PR Integration Test| N[user-svc-integration-test]
    N -->|PR Push Image| O[Push docker image]

    P[auth-svc-workflow] -->|Push| Q[auth-svc-push]
    P -->|PR Integration Test| R[auth-svc-integration-test]
    R -->|PR Push Image| S[auth-svc-pr-push-image]

    T[gateway-svc-workflow] -->|Push| U[gateway-svc-push]
    T -->|PR| V[gateway-svc-pr]
```
## Deploying workflow
![deploy](../../draft/output/deployment-example-workflow.png)
## Caching
![caching](../../draft/output/caching.png)
## Code quality (SonarQube), failed because I'm not write full test for the project yet :((
![code-quality](../../draft/output/sonarqube.png)

## Snyk Security scanning,vulnerability check
![snyk](../../draft/output/snyk.png)


# **For more detail about how I deploy this repo in Kubernetes, please refer to the [deployment repository](https://github.com/ngodat0103/yamp-deployment.git)** 
