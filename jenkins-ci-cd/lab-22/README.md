# Lab 22: Jenkins CI/CD Pipeline for Java Application

## Overview
Complete CI/CD pipeline using Jenkins to build, test, containerize, and deploy a Java Spring Boot application to Kubernetes.

## Architecture
- **Source Code**: Java Spring Boot application
- **CI/CD Tool**: Jenkins
- **Container Registry**: DockerHub
- **Deployment Target**: Kubernetes cluster
- **Authentication**: ServiceAccount with RBAC

## Prerequisites
- Jenkins server with Docker access
- Kubernetes cluster with kubectl configured
- DockerHub account
- Maven and Java 8 installed

## Project Structure
```
lab-22/
├── Jenkins_App/
│   ├── src/main/java/com/example/demo/
│   │   └── DemoApplication.java
│   ├── pom.xml
│   ├── Dockerfile
│   └── Jenkinsfile
├── K8s_App/
│   └── deployment.yaml
└── K8s_RBAC/
    ├── service-account.yaml
    ├── role.yaml
    └── role-binding.yaml
```

## Setup Instructions

### 1. RBAC Configuration
```bash
# Create ServiceAccount for Jenkins
kubectl apply -f K8s_RBAC/service-account.yaml
kubectl apply -f K8s_RBAC/role.yaml
kubectl apply -f K8s_RBAC/role-binding.yaml

# Get ServiceAccount token
kubectl get secret jenkins-sa-token -n ivolve -o jsonpath='{.data.token}' | base64 -d
```

## Pipeline Stages

### 1. Unit Tests
```groovy
sh 'cd jenkins-ci-cd/lab-22/Jenkins_App && mvn test'
```

### 2. Build Application
```groovy
sh 'cd jenkins-ci-cd/lab-22/Jenkins_App && mvn package'
```

### 3. Docker Build
```groovy
sh 'cd jenkins-ci-cd/lab-22/Jenkins_App && docker build -t $IMAGE_NAME:$BUILD_NUMBER .'
```

### 4. Docker Push
```groovy
docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD
docker push $IMAGE_NAME:$BUILD_NUMBER
```

### 5. Update Deployment
```groovy
sed -i "s|image: .*|image: $IMAGE_NAME:$BUILD_NUMBER|" $DEPLOYMENT_FILE
```

### 6. Deploy to Kubernetes
```groovy
kubectl apply -f $DEPLOYMENT_FILE -n ivolve --server=$API_SERVER --token=$TOKEN --insecure-skip-tls-verify=true --validate=false
```

## Verification
```bash
# Check deployment status
kubectl get pods -n ivolve
kubectl get deployment nodejs-app -n ivolve
```

## Results
- ✅ Automated CI/CD pipeline
- ✅ Java application built and tested
- ✅ Docker image created and pushed
- ✅ Kubernetes deployment updated
- ✅ Application running in cluster
<img width="1543" height="720" alt="image" src="https://github.com/user-attachments/assets/1aeda988-25c5-45f7-ab9a-430115ae8dac" />
<img width="1539" height="730" alt="image" src="https://github.com/user-attachments/assets/6a2049be-9b21-4165-88f1-73fcb5b5a1e8" />
<img width="915" height="145" alt="image" src="https://github.com/user-attachments/assets/7ead4145-2a1d-49f8-ac8c-6cfccfd9cf91" />


## Author
**Anas Tarek** 
