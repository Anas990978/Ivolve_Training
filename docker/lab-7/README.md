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
Hello from Bind Mount

```

### Step 8: Access Container Shell
```bash
docker exec -it nginx-container sh
/ # cd ls usr/share/nginx/html/
/usr/share/nginx/html/ cat index.html
Hello from Bind Mount
/usr/share/nginx/html # echo "hi from nti" > index.html

```

### Step 9: Verify logs are stored in the nginx_logs volume.
```bash
sudo ls /var/lib/docker/volumes/nginx_logs/_data
docker stop nginx-container
docker rm nginx-container
docker volume rm nginx_logs
```

## Expected Output
<img width="1370" height="572" alt="image" src="https://github.com/user-attachments/assets/8c1b5768-c134-4943-9fa9-b792ce2c9c98" />
<img width="1273" height="320" alt="image" src="https://github.com/user-attachments/assets/72dc5191-971b-489f-b57d-0ccedec1ad2c" />
<img width="1366" height="706" alt="image" src="https://github.com/user-attachments/assets/cb6bd4a4-d709-4e72-bf67-e89c84e8cb4e" />
<img width="1365" height="711" alt="image" src="https://github.com/user-attachments/assets/15df26e3-d57d-4cef-8daf-16042ca20331" />

## Author
**Anas Tarek** 
