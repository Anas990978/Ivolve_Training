# Lab 17: Pod Resource Management with CPU and Memory Requests and Limits

## Objective
Update the existing Node.js Deployment to include resource requests and limits for CPU and memory management.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured
- Namespace `ivolve` available
- Node.js Deployment from lab-16

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-17
cd lab-17
```

### Step 2: Copy Existing Deployment
```bash
cp ../lab-16/nodejs-deployment.yaml .
```

### Step 3: Edit Deployment to Add Resource Management
```bash
vim nodejs-deployment.yaml
```

Add resource requests and limits to the main container:
```yaml
containers:
- image: anas587/kubernets-app-app
  name: nodejs-app
  ports:
  - containerPort: 3000
  envFrom:
  - configMapRef:
      name: mysql-config
  - secretRef:
      name: mysql-secret
  volumeMounts:
  - name: app-storage
    mountPath: /app/data
  resources:
    requests:
      cpu: "1"
      memory: "1Gi"
    limits:
      cpu: "2"
      memory: "2Gi"
```

### Step 4: Apply Updated Deployment
```bash
kubectl apply -f nodejs-deployment.yaml
```

### Step 5: Verify Resource Configuration
```bash
kubectl describe pod <pod-name> -n ivolve
```

### Step 6: Monitor Real-time Resource Usage
```bash
kubectl top pod <pod-name> -n ivolve
```

### Step 7: Check All Pods Resource Usage
```bash
kubectl get pods -n ivolve
kubectl top pods -n ivolve
```

## Expected Results
<img width="902" height="266" alt="image" src="https://github.com/user-attachments/assets/794386f1-a900-43d1-9887-89ed73e7ac26" />
<img width="1361" height="516" alt="image" src="https://github.com/user-attachments/assets/0836f309-dcef-4bdd-8bf4-a3c386b62b2b" />

## Author
**Anas Tarek**
