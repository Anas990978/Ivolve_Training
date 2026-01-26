# Lab 16: Kubernetes Init Container for Pre-Deployment Database Setup

## Objective
Modify the existing Node.js Deployment to include an init container that sets up MySQL database and user before the main application starts.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured
- Namespace `ivolve` available
- ConfigMap and Secret from lab-12
- MySQL StatefulSet from lab-14
- Node.js Deployment from lab-15

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-16
cd lab-16
```

### Step 2: Copy Existing Deployment
```bash
cp ../lab-15/nodejs-deployment.yaml .
```

### Step 3: Edit Deployment to Add Init Container
```bash
vim nodejs-deployment.yaml
```

Add initContainers section before containers:
```yaml
initContainers:
- name: mysql-init
  image: mysql:5.7
  envFrom:
  - configMapRef:
      name: mysql-config
  - secretRef:
      name: mysql-secret
  command:
  - /bin/bash
  - -c
  - |
    mysql -h mysql-headless.ivolve.svc.cluster.local -u root -p$MYSQL_ROOT_PASSWORD -e "CREATE DATABASE IF NOT EXISTS ivolve;"
    mysql -h mysql-headless.ivolve.svc.cluster.local -u root -p$MYSQL_ROOT_PASSWORD -e "CREATE USER IF NOT EXISTS 'anas22'@'%' IDENTIFIED BY 'anas12344';"
    mysql -h mysql-headless.ivolve.svc.cluster.local -u root -p$MYSQL_ROOT_PASSWORD -e "GRANT ALL PRIVILEGES ON ivolve.* TO 'anas22'@'%';"
    mysql -h mysql-headless.ivolve.svc.cluster.local -u root -p$MYSQL_ROOT_PASSWORD -e "FLUSH PRIVILEGES;"
```

### Step 4: Apply Updated Deployment
```bash
kubectl apply -f nodejs-deployment.yaml
```

### Step 5: Verify Init Container Execution
```bash
kubectl get pods -n ivolve
kubectl logs <pod-name> -c mysql-init -n ivolve
```

### Step 6: Verify Database Creation
```bash
kubectl exec -it mysql-statefulset-0 -n ivolve -- mysql -u root -proot123 -e "SHOW DATABASES;" | grep ivolve
```

### Step 7: Verify User Creation
```bash
kubectl exec -it mysql-statefulset-0 -n ivolve -- mysql -u root -proot123 -e "SELECT User, Host FROM mysql.user WHERE User='anas22';"
```

### Step 8: Test User Access
```bash
kubectl exec -it mysql-statefulset-0 -n ivolve -- mysql -u anas22 -panas12344 ivolve -e "SELECT 'Success: User can access ivolve database';"
```

### Step 9: Test User Privileges
```bash
kubectl exec -it mysql-statefulset-0 -n ivolve -- mysql -u anas22 -panas12344 ivolve -e "SHOW GRANTS FOR CURRENT_USER();"
```

## Expected Results
<img width="902" height="266" alt="image" src="https://github.com/user-attachments/assets/794386f1-a900-43d1-9887-89ed73e7ac26" />
<img width="1361" height="516" alt="image" src="https://github.com/user-attachments/assets/0836f309-dcef-4bdd-8bf4-a3c386b62b2b" />

## Author
**Anas Tarek**
