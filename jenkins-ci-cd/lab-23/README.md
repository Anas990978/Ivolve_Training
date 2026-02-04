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

### 2. Jenkins Configuration
1. **Install Jenkins** on Ubuntu VM
2. **Add jenkins user to docker group**:
   ```bash
   sudo usermod -aG docker jenkins
   sudo systemctl restart jenkins
   ```
3. **Configure Credentials**:
   - `DockerHub`: Username/Password for Docker registry
   - `ServiceAccount-Token`: Secret text with SA token
   - `API Server`: Secret text with Kubernetes API server URL

### 3. Pipeline Configuration
- **Source**: GitHub repository
- **Build Trigger**: GitHub webhook or manual
- **Pipeline Script**: From SCM (Jenkinsfile)

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

## Key Files

### Jenkinsfile
- Complete pipeline definition
- Environment variables for image name and deployment file
- Credential management for DockerHub and Kubernetes
- Error handling and cleanup

### pom.xml
- Spring Boot 2.7.18 (Java 8 compatible)
- Maven build configuration
- Dependencies for web application

### Dockerfile
```dockerfile
FROM openjdk:8-jre-slim
COPY target/*.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### deployment.yaml
- Kubernetes deployment with 2 replicas
- Resource requests: 100m CPU, 256Mi memory
- Resource limits: 500m CPU, 512Mi memory
- Service account and image pull secrets

## Troubleshooting

### Common Issues
1. **Maven compilation errors**: Check Java version compatibility
2. **Docker permission denied**: Add jenkins user to docker group
3. **Kubernetes connection failed**: Verify API server URL and token
4. **Pod scheduling failed**: Check resource quotas and node capacity
5. **Image pull errors**: Verify DockerHub credentials and image name

### Resource Management
- Remove resource quotas if blocking pod creation
- Adjust CPU/memory requests based on node capacity
- Monitor node resource allocation

## Verification
```bash
# Check deployment status
kubectl get pods -n ivolve
kubectl get deployment nodejs-app -n ivolve

# Access application
kubectl port-forward svc/nodejs-service 3001:80 -n ivolve
curl http://localhost:3001
```

## Results
- ✅ Automated CI/CD pipeline
- ✅ Java application built and tested
- ✅ Docker image created and pushed
- ✅ Kubernetes deployment updated
- ✅ Application running in cluster

## Security Features
- ServiceAccount-based authentication
- RBAC with minimal required permissions
- Secure credential management in Jenkins
- Image scanning and validation