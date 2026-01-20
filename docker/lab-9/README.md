# Lab 9: Containerized Node.js and MySQL Stack Using Docker Compose

## Objective
Deploy a Node.js application with MySQL database using Docker Compose and push the image to DockerHub.

## Prerequisites
- Linux/WSL environment
- Docker and Docker Compose installed
- DockerHub account

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir -p lab-9
cd lab-9
```

### Step 2: Clone Repository
```bash
git clone https://github.com/Ibrahim-Adel15/kubernets-app.git
cd kubernets-app
```

### Step 3: Create docker-compose.yml File
```bash
vim docker-compose.yaml
```

Add the following content:
```yaml
version: "3.8"
services:
  app:
    build: .
    container_name: web-app
    ports:
      - "3001:3000"
    environment:
      DB_HOST: db
      DB_USER: appuser
      DB_PASSWORD: pass456
    depends_on:
      - db

  db:
    image: mysql:8
    container_name: mysql-db
    environment:
      MYSQL_ROOT_PASSWORD: pass123
      MYSQL_DATABASE: ivolve
      MYSQL_USER: appuser
      MYSQL_PASSWORD: pass456
    volumes:
      - db_data:/var/lib/mysql

volumes:
  db_data:
```

### Step 4: Build and Start Services
```bash
docker-compose up -d
```

### Step 5: Verify Containers are Running
```bash
docker-compose ps
```

### Step 6: Check Container Logs
```bash
docker logs mysql-db
```

```bash
docker logs web-app
```

### Step 7: Verify App is Working
```bash
curl http://localhost:3001
```

### Step 8: Verify Health Endpoint
```bash
curl http://localhost:3001/health
```

### Step 9: Verify Ready Endpoint
```bash
curl http://localhost:3001/ready
```

### Step 10: Check Application Logs
```bash
docker-compose exec app cat /app/logs/access.log
```

### Step 11: Login to DockerHub
```bash
docker login -u anas587
```

### Step 12: List Docker Images
```bash
docker images
```

### Step 13: Tag Image for DockerHub
```bash
docker tag kubernets-app-app:latest anas587/kubernets-app-app:latest
```

### Step 14: Push Image to DockerHub
```bash
docker push anas587/kubernets-app-app:latest
```

### Step 15: Remove Volumes (Optional)
```bash
docker-compose down -v
```

## Expected Results
<img width="1365" height="703" alt="image" src="https://github.com/user-attachments/assets/09e577b1-60f8-411c-b1a8-1622f2bcdbc5" />
<img width="1355" height="149" alt="image" src="https://github.com/user-attachments/assets/48f4d343-29c9-42f1-b737-161439b0e60c" />
<img width="1367" height="603" alt="image" src="https://github.com/user-attachments/assets/d192f544-e876-49d9-b92d-9dd511e3130d" />
<img width="1363" height="687" alt="image" src="https://github.com/user-attachments/assets/380f3490-ee3b-44d3-9226-61da6024e197" />
<img width="1367" height="436" alt="image" src="https://github.com/user-attachments/assets/60310f4f-6323-4b67-bb50-1bf44f040bde" />
<img width="1370" height="274" alt="image" src="https://github.com/user-attachments/assets/67e412a4-c78d-4272-8cbd-99e97d8ef5d6" />
<img width="1366" height="173" alt="image" src="https://github.com/user-attachments/assets/3308bac2-0b0a-4280-9eb5-dd3973ab796d" />

## Author
**Anas Tarek**
