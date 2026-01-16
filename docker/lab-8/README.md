# Lab 8: Custom Docker Network for Microservices

## Objective
Create custom Docker network and verify container communication between frontend and backend microservices.

## Prerequisites
- Linux/WSL environment
- Docker installed and running

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-8
cd lab-8
```

### Step 2: Clone Repository
```bash
git clone https://github.com/Ibrahim-Adel15/Docker5.git
cd Docker5
```

### Step 3: Create Dockerfile for Frontend
```bash
FROM python:3.9-slim
WORKDIR /app
COPY . .
RUN pip install -r requirements.txt
EXPOSE 5000
CMD ["python", "app.py"]
```

### Step 4: Create Dockerfile for Backend
```bash
FROM python:3.9-slim
WORKDIR /app
COPY . .
RUN pip install flask
EXPOSE 5000
CMD ["python", "app.py"]
```

### Step 5: Build Frontend Image
```bash
docker build -t front-image .
```

### Step 6: Build Backend Image
```bash
docker build -t back-image .
```

### Step 7: Create Custom Network
```bash
docker network create ivolve-network
```

### Step 8: Verify Network Creation
```bash
docker network ls
```

### Step 9: Run Backend Container
```bash
docker run -d --name back-container --network ivolve-network -p 5002:5000 back-image
```

### Step 10: Run Frontend Container (frontend1) on Custom Network
```bash
docker run -d --name front-container --network ivolve-network -p 5001:5000 front-image
```

### Step 11: Run Frontend Container (frontend2) on Default Network
```bash
docker run -d --name front-container2 -p 5003:5000 front-image
```

### Step 12: Verify Containers are Running
```bash
docker ps
```
### Step 13: Test Communication - Frontend1 to Backend (Should Work)
```bash
docker -it exec front-container bash
ping -c 3 back-container
```

### Step 14: Test Communication - Frontend2 to Backend (Should Fail)
```bash
docker -it exec front-container2 bash
ping -c 3 back-container
```

### Step 15: Inspect Network
```bash
docker network inspect ivolve-network
```

### Step 16: Stop All Containers
```bash
docker stop back-container front-container front-container2
```

### Step 17: Remove All Containers
```bash
docker rm back-container front-container front-container2
```

### Step 18: Delete Custom Network
```bash
docker network rm ivolve-network
```

### Step 19: Verify Network Deletion
```bash
docker network ls
```

## Expected Results
<img width="958" height="539" alt="image" src="https://github.com/user-attachments/assets/f0f61927-f84f-4c7f-8e6a-bd2c08aa0437" />
<img width="1363" height="521" alt="image" src="https://github.com/user-attachments/assets/bdd52cce-464a-4d1b-9b11-23b0748e54ff" />
<img width="1365" height="672" alt="image" src="https://github.com/user-attachments/assets/66ef5ea6-030b-4dc3-98a6-969aa54815a8" />
<img width="975" height="197" alt="image" src="https://github.com/user-attachments/assets/9e8877e6-f9ea-407b-ab72-f3c27b407656" />
<img width="1360" height="83" alt="image" src="https://github.com/user-attachments/assets/623d45d9-3067-43e9-bc96-571fa9cae3cd" />
<img width="1360" height="83" alt="image" src="https://github.com/user-attachments/assets/c3de28b7-ce91-4fc7-a533-7c8e5e56756f" />

## Author
**Anas Tarek**
