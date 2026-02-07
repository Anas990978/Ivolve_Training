# Lab 24: Multi-branch CI/CD Workflow

This lab demonstrates a **multi-branch CI/CD pipeline** using Jenkins, Git, Docker, and Kubernetes.

---

## Objective

- Automate deployments based on Git branches (`dev`, `stag`, `prod`).
- Use a **Jenkins multibranch pipeline** to deploy to corresponding **Kubernetes namespaces**.
- Utilize a **shared library** for common pipeline steps.

---

## Steps

### 1. Git Repository Setup

1. Clone the Dockerfile from:

https://github.com/Ibrahim-Adel15/Jenkins_App.git


2. Push it to your own repository.
3. Create three branches:



dev
stag
prod


### 2. Kubernetes Setup

1. Create three namespaces:

```bash
kubectl create namespace dev
kubectl create namespace stag
kubectl create namespace prod
```

Prepare deployment YAMLs for each environment:
```
cp ../../lab-23/Jenkins_App/deployment.yaml ./dev-deployment.yaml
cp ../../lab-23/Jenkins_App/deployment.yaml ./stag-deployment.yaml
cp ../../lab-23/Jenkins_App/deployment.yaml ./prod-deployment.yaml
```

Edit the YAML files as needed for each environment:
```
vim dev-deployment.yaml
vim stag-deployment.yaml
vim prod-deployment.yaml
```
### 3. Jenkins Multibranch Pipeline

Create a multibranch pipeline in Jenkins.

Connect it to your GitHub repository.

Configure branch sources:

dev → deploys to dev namespace

stag → deploys to stag namespace

prod → deploys to prod namespace

Use a Jenkins slave node to run the pipeline.

Reference a Shared Library for common CI/CD steps (build, test, deploy).

4. Pipeline Workflow

For each branch:

Jenkins pulls the latest code.

Builds the Docker image.

Pushes the Docker image to your registry.

Deploys to the corresponding Kubernetes namespace using the deployment YAML.

### Auther
**Anas Tarek**
