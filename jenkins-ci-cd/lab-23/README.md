# Lab 23: CI/CD Pipeline Implementation with Jenkins Agents and Shared Libraries

## Objective
Implement a complete CI/CD pipeline using Jenkins with shared libraries and agent configuration for automated build, test, scan, and deployment.

## Prerequisites
- Jenkins installed and running
- Docker installed
- Maven installed
- kubectl configured
- Trivy security scanner installed
- Kubernetes cluster access
- DockerHub account

## Project Structure
```
jenkins-ci-cd/lab-23/
├── Jenkins_App/
│   ├── src/
│   ├── Dockerfile
│   ├── pom.xml
│   ├── deployment.yaml
│   └── Jenkinsfile
└── README.md

vars/
├── buildApp.groovy
├── buildAndpushImage.groovy
├── scanDockerImage.groovy
├── sonarQubeAnalysis.groovy
└── deployToK8s.groovy
```

## Pipeline Stages

### 1. Build and Test
- Runs Maven unit tests
- Compiles and packages the application
- Uses shared library: `buildApp()`

### 2. SonarQube Analysis
- Performs code quality analysis
- Scans for code smells and vulnerabilities
- Uses shared library: `sonarQubeAnalysis()`

### 3. Docker Build and Push
- Builds Docker image with build number tag
- Tags image as latest
- Pushes to DockerHub
- Uses shared library: `buildAndpushImage()`

### 4. Image Security Scan
- Scans Docker image with Trivy
- Checks for HIGH and CRITICAL vulnerabilities
- Uses shared library: `scanDockerImage()`

### 5. Deploy to Kubernetes
- Updates deployment with new image tag
- Deploys to Kubernetes cluster
- Uses shared library: `deployToK8s()`

## Shared Library Functions

### buildApp.groovy
```groovy
def call() {
    sh 'mvn test'
    sh 'mvn package'
}
```

### buildAndpushImage.groovy
```groovy
def call(String imageName, String dockerhubCredID) {
    sh "docker build -t ${imageName}:${env.BUILD_NUMBER} ."
    sh "docker tag ${imageName}:${env.BUILD_NUMBER} ${imageName}:latest"
    withCredentials([usernamePassword(credentialsId: dockerhubCredID, usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh "docker login -u \$USER -p \$PASS"
        sh "docker push ${imageName}:${env.BUILD_NUMBER}"
        sh "docker push ${imageName}:latest"
    }
}
```

### scanDockerImage.groovy
```groovy
def call(String imageName) {
    sh "trivy image --severity HIGH,CRITICAL --timeout 5m ${imageName}:${env.BUILD_NUMBER}"
}
```

### deployToK8s.groovy
```groovy
def call(String imageName, String deploymentFile, String apiServerCredID, String tokenCredID) {
    sh "sed -i 's|image: .*|image: ${imageName}:${BUILD_NUMBER}|' ${deploymentFile}"
    withCredentials([
        string(credentialsId: tokenCredID, variable: 'TOKEN'),
        string(credentialsId: apiServerCredID, variable: 'API_SERVER')
    ]) {
        sh "kubectl apply -f ${deploymentFile} --server=\$API_SERVER --token=\$TOKEN --insecure-skip-tls-verify=true --validate=false"
    }
}
```

## Jenkins Agent Configuration

### 1. Create Agent Directory
```bash
sudo mkdir -p /home/jenkins/agent
sudo chown jenkins:jenkins /home/jenkins/agent
sudo chmod 755 /home/jenkins/agent
```

### 2. Configure Agent in Jenkins UI
- Navigate to: Manage Jenkins → Nodes → New Node
- Name: `jenkins-agent-1`
- Type: Permanent Agent
- Remote root directory: `/home/jenkins/agent`
- Labels: `jenkins-agent-1`
- Launch method: Launch agent by connecting it to the controller

### 3. Start the Agent
```bash
cd /home/jenkins/agent
echo "YOUR_SECRET" > secret-file
curl -sO http://JENKINS_URL:8080/jnlpJars/agent.jar
java -jar agent.jar -url http://JENKINS_URL:8080/ -secret @secret-file -name "jenkins-agent-1" -webSocket -workDir "/home/jenkins/agent" &
```

## Jenkins Credentials Setup

Configure the following credentials in Jenkins:

1. **DockerHub** (Username with password)
   - ID: `DockerHub`
   - Username: Your DockerHub username
   - Password: Your DockerHub password/token

2. **ServiceAccount-Token** (Secret text)
   - ID: `ServiceAccount-Token`
   - Secret: Kubernetes service account token

3. **API Server** (Secret text)
   - ID: `API Server`
   - Secret: Kubernetes API server URL

4. **SONAR_TOKEN** (Secret text)
   - ID: `SONAR_TOKEN`
   - Secret: SonarQube authentication token

## Shared Library Configuration

1. Navigate to: Manage Jenkins → System → Global Pipeline Libraries
2. Add library:
   - Name: `shared-lib`
   - Default version: `main`
   - Retrieval method: Modern SCM
   - Source Code Management: Git
   - Project Repository: `https://github.com/YOUR_USERNAME/YOUR_REPO.git`
   - Library Path: (leave empty)

## Running the Pipeline

1. Create a new Pipeline job in Jenkins
2. Configure SCM:
   - Repository URL: Your GitHub repository
   - Script Path: `jenkins-ci-cd/lab-23/Jenkins_App/Jenkinsfile`
3. Click "Build Now"

## Pipeline Execution Flow

```
Start → Build & Test → SonarQube Analysis → Docker Build & Push → 
Security Scan → Deploy to K8s → End
```

## Verification

### Check Pipeline Status
- View build logs in Jenkins console output
- Verify each stage completes successfully

### Verify Docker Image
```bash
docker images | grep kubernets-app-app
```

### Verify Kubernetes Deployment
```bash
kubectl get deployments
kubectl get pods
kubectl get services
```

## Troubleshooting

### Agent Connection Issues
- Verify agent is online in Jenkins UI
- Check agent logs: `/home/jenkins/agent/agent.log`
- Restart agent if needed

### Docker Build Failures
- Ensure Docker daemon is running
- Check Dockerfile syntax
- Verify base image availability

### Kubernetes Deployment Failures
- Verify kubectl configuration
- Check service account permissions
- Validate deployment.yaml syntax

## Best Practices

1. **Use Shared Libraries** - Reusable code across multiple pipelines
2. **Agent Labels** - Distribute workload across multiple agents
3. **Credentials Management** - Store sensitive data securely in Jenkins
4. **Image Tagging** - Use build numbers for traceability
5. **Security Scanning** - Always scan images before deployment
6. **Validation** - Test deployments in staging before production

## Conclusion

This lab demonstrates a complete CI/CD pipeline implementation using Jenkins with:
- Shared libraries for code reusability
- Jenkins agents for distributed builds
- Automated testing and security scanning
- Containerized deployment to Kubernetes
