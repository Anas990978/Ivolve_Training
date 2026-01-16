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

### Step 15: Verify Database Connection
```bash
docker-compose exec db mysql -uroot -ppass123 -e "SHOW DATABASES;"
```

### Step 16: Stop Services
```bash
docker-compose down
```

### Step 17: Remove Volumes (Optional)
```bash
docker-compose down -v
```

## Expected Results
- App accessible at http://localhost:3001
- Health endpoint returns status message
- Ready endpoint returns status message
- Access logs available at /app/logs/access.log
- Image successfully pushed to DockerHub: anas587/kubernets-app-app:latest

## Notes
- The app is mapped to port 3001 on the host (not 3000)
- MySQL database 'ivolve' is automatically created
- Application logs are stored in /app/logs/ directory inside the container
- db_data volume persists MySQL data

## Author
**Anas Tarek**
