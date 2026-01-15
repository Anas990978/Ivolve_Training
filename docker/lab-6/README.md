# Lab 6: Docker Environment Variables

## Objective
Build and run Python Flask application with different environment variable configurations.

## Prerequisites
- Linux/WSL environment
- Docker installed and running

## Lab Steps

### Step 1: Create Lab Directory and Clone Application
```bash
mkdir lab-6
cd lab-6
git clone https://github.com/Ibrahim-Adel15/Docker-3.git
cd Docker-3
```

### Step 2: Create Dockerfile
```dockerfile
FROM python:3.15.0a3-alpine3.23
WORKDIR /lab6
COPY . .
RUN pip install flask
EXPOSE 8080
CMD ["python","app.py"]
```

### Step 3: Build image1
```bash
docker build -t image1 .
```

### Step 4: Run container1 with Environment Variables
```bash
docker run -d --name container1 -p 8090:8080 -e APP_MODE=development -e APP_REGION=us-east image1:latest
```

### Step 5: Create Environment File
Create `env` file:
```
APP_MODE=staging
APP_REGION=us-west
```

### Step 6: Run container2 with Environment File
```bash
docker run -d --name container2 -p 8091:8080 --env-file env image1:latest
```

### Step 7: Create Dockerfile2 with Default ENV
```dockerfile
FROM python:3.15.0a3-alpine3.23
WORKDIR /lab6
COPY . .
ENV APP_MODE=prod  APP_REGION=canada-west
RUN pip install flask
EXPOSE 8080
CMD ["python","app.py"]
```

### Step 8: Build image2 and Run container3
```bash
docker build -f Dockerfile2 -t image2 .
docker run -d --name container3 -p 8092:8080 image2:latest
```

### Step 9: Test All Containers
```bash
curl http://localhost:8090
curl http://localhost:8091
curl http://localhost:8092
```

## Expected Output
<img width="1359" height="514" alt="image" src="https://github.com/user-attachments/assets/6c4d07d4-65d6-4142-9dd2-9a4a19c08acb" />


## Author
**Anas Tarek** 
