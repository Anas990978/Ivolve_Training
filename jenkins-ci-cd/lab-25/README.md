# Lab 25: GitOps Workflow (Argo CD)

This lab sets up a GitOps flow using Argo CD and Jenkins:
- Jenkins builds the app and Docker image
- Pushes the image to Docker Hub
- Updates `deployment.yaml` in a GitOps repo
- Argo CD watches the GitOps repo and syncs to the cluster

## 1) Configure Argo CD
You already installed Argo CD. If not:

```bash
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
```

Create the Argo CD Application:
- Edit `jenkins-ci-cd/lab-25/argocd/application.yaml` and replace `REPLACE_GITOPS_REPO_URL`.

```bash
kubectl apply -f jenkins-ci-cd/lab-25/argocd/application.yaml
```

## 2) GitOps Repo Structure
Ensure your GitOps repo contains:
- `jenkins-ci-cd/lab-25/gitops/deployment.yaml`
- `jenkins-ci-cd/lab-25/gitops/service.yaml`

Use the manifests from:
- `jenkins-ci-cd/lab-25/gitops/deployment.yaml`
- `jenkins-ci-cd/lab-25/gitops/service.yaml`

## 3) Jenkins Pipeline
The Jenkins pipeline lives at:
- `jenkins-ci-cd/lab-25/Jenkinsfile`

Required Jenkins credentials:
- `dockerhub-creds` (username/password)
- `git-https-creds` (username/token or username/password for GitHub)

Pipeline parameters:
- `APP_REPO_URL`: app source repo
- `GITOPS_REPO_URL`: GitOps repo to update
- `IMAGE_NAME`, `IMAGE_TAG`
- `GITOPS_PATH`: where `deployment.yaml` lives in GitOps repo

## 4) Validate GitOps
Once the pipeline updates the GitOps repo, Argo CD should auto-sync.
Check:

```bash
kubectl -n argocd get applications
kubectl -n argocd get app jenkins-app
```

