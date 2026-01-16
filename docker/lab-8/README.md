# Lab 8: Docker Networking - Multi-Container Application

## Objective
Create a custom Docker network and demonstrate container communication between frontend and backend services.

## Steps

### 1. Clone the Repository
```bash
cd lab-8
git clone https://github.com/Ibrahim-Adel15/Docker5.git
cd Docker5
```

### 2. Build Frontend Image
```bash
cd frontend
docker build -t front-image .
cd ..
```

### 3. Build Backend Image
```bash
cd backend
docker build -t back-image .
cd ..
```

### 4. Create Custom Docker Network
```bash
docker network create ivolve-network
```

### 5. Verify Network Creation
```bash
docker network ls
```

### 6. Run Backend Container on Custom Network
```bash
docker run -d --name back-container --network ivolve-network -p 5002:5000 back-image:latest
```

### 7. Run Frontend Container on Custom Network
```bash
docker run -d --name front-container --network ivolve-network -p 5001:5000 front-image:latest
```

### 8. Run Frontend Container on Default Network
```bash
docker run -d --name front-container2 -p 5003:5000 front-image:latest
```

### 9. Verify Containers are Running
```bash
docker ps
```

### 10. Inspect Custom Network
```bash
docker network inspect ivolve-network
```

### 11. Test Communication from Frontend to Backend
```bash
docker exec front-container curl http://back-container:5000
```

### 12. Access Frontend Container Shell
```bash
docker exec -it front-container bash
```

## Expected Results

- **front-container** (on ivolve-network): Successfully communicates with back-container ✓
- **front-container2** (on default network): Cannot resolve back-container hostname ✗
- Custom networks provide automatic DNS resolution between containers
- Default bridge network doesn't support DNS resolution by container name

## Cleanup

```bash
docker stop front-container back-container front-container2
docker rm front-container back-container front-container2
docker network rm ivolve-network
```

## Author
**Anas** - iVolve Training Labs