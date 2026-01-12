# Lab 3: Run Java Spring Boot App in a Container

## Objective
Clone Spring Boot application, write Dockerfile, build and run containerized application.

## Prerequisites
- Linux/WSL environment
- Docker installed and running

## Lab Steps

### Step 1: Clone Application Code
```bash
git clone https://github.com/Ibrahim-Adel15/Docker-1.git
cd Docker-1
```

### Step 2: Write Dockerfile
```dockerfile
FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /lab3

COPY ./Docker-1 .

RUN mvn package 

EXPOSE 8080

CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
```

### Step 3: Build app1 Image
```bash
docker build -t app .
```

### Step 4: Run container1 from app1 Image
```bash
docker run --name container1 -p 8080:8080 -d app1
```

### Step 5: Test the Application
```bash
curl http://localhost:8080
```

### Step 6: Stop and Delete Container
```bash
docker stop container1
docker rm container1
```

## Expected Output
- Docker image builds successfully
- Container runs on port 8080
- Application responds to HTTP requests
- Container stops and removes cleanly

## Author
**Anas** - iVolve Training Labs
