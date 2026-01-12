# Lab 3 — Run Spring Boot Application in Docker Container

## Objective
Build and run a Spring Boot application inside a Docker container.

## Prerequisites
- Linux/WSL environment
- Docker installed and running
- Java 17 (for local builds)

## Lab Steps

### Step 1: Navigate to Project Directory
```bash
cd docker/lab-3
```

### Step 2: Build Docker Image
```bash
docker build -t app1:latest .
```

### Step 3: Run Container
```bash
docker run --name container1 -p 8080:8080 -d app1:latest
```

### Step 4: Test the Application
```bash
curl http://localhost:8080/
```

### Step 5: View Container Logs
```bash
docker logs container1
```

### Step 6: Stop and Remove Container
```bash
docker stop container1
docker rm container1
```

## Alternative Run Command
If the image uses Maven entrypoint, override it:
```bash
docker run --name container1 -p 8080:8080 -d --entrypoint java app1:latest -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Expected Output
- Docker image builds successfully
- Container starts and runs on port 8080
- Application responds to HTTP requests
- Container logs show Spring Boot startup messages

## Notes
- The Dockerfile contains multi-stage build for optimized image size
- Application is accessible on `http://localhost:8080`
- For production, consider using JRE base images instead of JDK

## Author
**Anas** - iVolve Training Labs