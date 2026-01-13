# Lab 5: Multi-Stage Docker Build for Spring Boot App

## Objective
Clone Spring Boot application, create multi-stage Dockerfile for optimized image size, build and run containerized application.

## Prerequisites
- Linux/WSL environment
- Docker installed and running

## Lab Steps

### Step 1: Create Lab Directory and Clone Application
```bash
mkdir lab-5
cd lab-5
git clone https://github.com/Ibrahim-Adel15/Docker-1.git
```

### Step 2: Create Multi-Stage Dockerfile
```dockerfile
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /lab5
COPY Docker-1/ .
RUN mvn package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /lab5
COPY --from=build /lab5/target/demo-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
```

### Step 3: Build app3 Image
```bash
docker build -t app3 .
```

### Step 4: Run container3 from app3 Image
```bash
docker run -d --name container3 -p 8088:8080 app3:latest
```

### Step 5: Test the Application
```bash
curl http://localhost:8088
```

### Step 6: Stop and Clean Up
```bash
docker stop container3
docker rm container3
```

## Expected Output
<img width="1354" height="719" alt="image" src="https://github.com/user-attachments/assets/57242a4f-b6bc-4171-ac81-b20b8d50d624" />
<img width="1204" height="383" alt="image" src="https://github.com/user-attachments/assets/d61e7e19-3770-4655-8a33-34dc97440223" />
<img width="1356" height="229" alt="image" src="https://github.com/user-attachments/assets/b60c1887-7e5e-4860-bcb3-22dcd16df802" />


## Author
**Anas Tarek** 
