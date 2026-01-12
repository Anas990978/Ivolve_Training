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
docker build -t app1 .
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
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/e9b45e19-2981-4dd5-b019-4fcf5f3a827b" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/e57bc57a-2429-422f-b0ac-7d9a036983de" />



## Author
**Anas Tarek** 
