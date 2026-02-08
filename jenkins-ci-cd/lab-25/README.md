# Lab 25: GitOps Workflow with Jenkins and ArgoCD

## Objective
Implement a GitOps workflow using Jenkins for CI and ArgoCD for CD to automate application deployment to Kubernetes.

## Prerequisites
- Kubernetes cluster (Minikube)
- Jenkins installed and configured
- kubectl configured
- Docker Hub account
- GitHub account

## Lab Steps

### Step 1: Install ArgoCD

```bash
# Create ArgoCD namespace
kubectl create namespace argocd

# Install ArgoCD
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

# Get ArgoCD admin password
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d > argo-cd-pass.txt

# Port forward to access ArgoCD UI
kubectl -n argocd port-forward svc/argocd-server 8081:443 --address 0.0.0.0
```

Access ArgoCD UI at `https://localhost:8081`
- Username: `admin`
- Password: (from argo-cd-pass.txt)

### Step 2: Clone Application Repository

```bash
cd ~/Ivolve_Training/jenkins-ci-cd/lab-25/

# Clone the application
git clone https://github.com/Ibrahim-Adel15/Jenkins_App.git

# Move Jenkinsfile
mv Jenkinsfile ./Jenkins_App/

# Create GitOps directory
cd gitops/
cp ../../lab-23/Jenkins_App/deployment.yaml ./deployment.yaml
cat deployment.yaml
```

### Step 3: Push to GitHub

```bash
cd ~/Ivolve_Training
git add .
git commit -m "lab-25 jenkins app"

# Set remote URL to HTTPS
git remote set-url origin https://github.com/Anas990978/Ivolve_Training.git
git push origin main
```

### Step 4: Configure Jenkins Credentials

#### Create GitHub Personal Access Token
1. Go to GitHub Settings → Developer settings → Personal access tokens
2. Generate new token with `repo` scope
3. Copy the token

#### Add Credentials to Jenkins
1. Jenkins → Manage Jenkins → Credentials → Global credentials
2. Add GitHub credentials:
   - Username: Your GitHub username
   - Password: Personal Access Token
   - ID: `GitHub`
3. Add Docker Hub credentials:
   - Username: Your Docker Hub username
   - Password: Your Docker Hub password
   - ID: `DockerHub`

### Step 5: Create Jenkinsfile

Create `Jenkinsfile` in `jenkins-ci-cd/lab-25/Jenkins_App/`:

```groovy
pipeline {
    agent any
    
    environment {
        IMAGE_NAME = 'anas587/kubernets-app-app'
        DEPLOYMENT_FILE = 'deployment.yaml'
        WORK_DIR = 'jenkins-ci-cd/lab-25/Jenkins_App'
        DEPLOYMENT_DIR = 'jenkins-ci-cd/lab-25/gitops'
        GIT_CREDENTIALS_ID = 'GitHub'
        ARGOCD_REPO = 'https://github.com/Anas990978/Ivolve_Training.git'
        ARGOCD_BRANCH = 'main'
    }
    
    stages {
        stage('Run Unit Tests') {
            steps {
                dir("${WORK_DIR}") {
                    sh 'mvn test'
                }
            }
        }
        
        stage('Build Application') {
            steps {
                dir("${WORK_DIR}") {
                    sh 'mvn package'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                dir("${WORK_DIR}") {
                    sh 'docker build -t $IMAGE_NAME:$BUILD_NUMBER .'
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'DockerHub',
                    usernameVariable: 'DOCKERHUB_USERNAME',
                    passwordVariable: 'DOCKERHUB_PASSWORD'
                )]) {
                    sh '''
                        docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD
                        docker push $IMAGE_NAME:$BUILD_NUMBER
                    '''
                }
            }
        }
        
        stage('Delete Local Docker Image') {
            steps {
                sh 'docker rmi $IMAGE_NAME:$BUILD_NUMBER || true'
            }
        }
        
        stage('Clone ArgoCD GitOps Repo') {
            steps {
                sh 'rm -rf argocd-repo'
                dir('argocd-repo') {
                    git branch: "${ARGOCD_BRANCH}",
                        credentialsId: "${GIT_CREDENTIALS_ID}",
                        url: "${ARGOCD_REPO}"
                }
            }
        }
        
        stage('Update Deployment File and Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: GIT_CREDENTIALS_ID,
                    usernameVariable: 'GIT_USERNAME',
                    passwordVariable: 'GIT_PASSWORD'
                )]) {
                    dir("argocd-repo/${DEPLOYMENT_DIR}") {
                        sh """
                            git config user.email "jenkins@ci.com"
                            git config user.name "Jenkins"
                            sed -i 's|image: .*|image: ${IMAGE_NAME}:${BUILD_NUMBER}|' ${DEPLOYMENT_FILE}
                            git add ${DEPLOYMENT_FILE}
                            git commit -m "Update deployment image to ${IMAGE_NAME}:${BUILD_NUMBER} by Jenkins" || echo "No changes"
                            git push https://\${GIT_USERNAME}:\${GIT_PASSWORD}@github.com/Anas990978/Ivolve_Training.git ${ARGOCD_BRANCH}
                        """
                    }
                }
            }
        }
    }
    
    post {
        always {
            sh 'docker logout || true'
            sh 'rm -rf argocd-repo || true'
        }
    }
}
```

### Step 6: Create Jenkins Pipeline Job

1. Jenkins Dashboard → New Item → `Lab25-ArgoCD` → Pipeline
2. Pipeline section:
   - Definition: Pipeline script from SCM
   - SCM: Git
   - Repository URL: `https://github.com/Anas990978/Ivolve_Training.git`
   - Credentials: Select GitHub credentials
   - Branch: `*/main`
   - Script Path: `jenkins-ci-cd/lab-25/Jenkins_App/Jenkinsfile`
3. Save

### Step 7: Create ArgoCD Application

#### Option 1: Via ArgoCD UI
1. Login to ArgoCD UI: `https://localhost:8081`
2. Click **"+ NEW APP"**
3. Fill in:
   - Application Name: `jenkins-app`
   - Project: `default`
   - Sync Policy: **AUTOMATIC**
   - Check: **PRUNE RESOURCES** and **SELF HEAL**
   - Repository URL: `https://github.com/Anas990978/Ivolve_Training.git`
   - Revision: `main`
   - Path: `jenkins-ci-cd/lab-25/gitops`
   - Cluster URL: `https://kubernetes.default.svc`
   - Namespace: `ivolve`
4. Click **CREATE**




# Verify deployment file updated
```
cat jenkins-ci-cd/lab-25/gitops/deployment.yaml | grep image:
```

# Verify deployment in Kubernetes
```
kubectl get pods -n ivolve -o wide
kubectl get deployment -n ivolve -o yaml | grep image:
kubectl describe deployment -n ivolve | grep Image
```

### Expected Results
<img width="1554" height="739" alt="image" src="https://github.com/user-attachments/assets/2cd913ab-f636-40e4-a8d5-00c2f234bdf9" />
<img width="1124" height="203" alt="image" src="https://github.com/user-attachments/assets/9a8af5c5-6178-4fc0-adb9-51e78f6dee76" />
<img width="1839" height="816" alt="image" src="https://github.com/user-attachments/assets/e141b515-92dc-406e-bd3b-a82f63c58082" />



## Author
**Anas Tarek**
