# Lab 7: Docker Volumes and Bind Mounts

## Objective
Create and use Docker volumes and bind mounts with Nginx container.

## Prerequisites
- Linux/WSL environment
- Docker installed and running

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-7
cd lab-7
```

### Step 2: Create Docker Volume
```bash
docker volume create nginx_logs
```

### Step 3: Verify Volume Creation
```bash
docker volume ls
```

### Step 4: Create Bind Mount Directory and HTML File
```bash
mkdir -p nginx-bind/html
echo "Hello from Bind Mount" > nginx-bind/html/index.html
```

### Step 5: Verify HTML File
```bash
cat nginx-bind/html/index.html
Hello from Bind Mount
```

### Step 6: Run Nginx Container with Volume and Bind Mount
```bash
docker run -d --name nginx-container -p 8093:80 \
  -v nginx_logs:/var/log/nginx \
  -v /home/anas/Ivolve_Training/docker/lab-7/nginx-bind/html/:/usr/share/nginx/html \
  nginx:alpine
```

### Step 7: Test Nginx Server
```bash
curl http://localhost:8093
```

### Step 8: Access Container Shell
```bash
docker exec -it nginx-container sh
/ # cd ls usr/share/nginx/html/
/usr/share/nginx/html/ cat index.html
Hello from Bind Mount
/usr/share/nginx/html # echo "hi from nti" > index.html

```

### Step 9: Clean Up
```bash
docker stop nginx-container
docker rm nginx-container
docker volume rm nginx_logs
```

## Expected Output

## Author
**Anas Tarek** 