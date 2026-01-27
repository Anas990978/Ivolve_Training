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
kubectl describe pod nodejs-app-8695df9d44-8phms -n ivolve
```

### Step 6: Monitor Real-time Resource Usage
```bash
kubectl top pod nodejs-app-8695df9d44-8phms -n ivolve
```

## Expected Results
<img width="1224" height="471" alt="image" src="https://github.com/user-attachments/assets/1cda4a77-4503-4d5a-86d7-f1d9b3116b75" />
<img width="954" height="73" alt="image" src="https://github.com/user-attachments/assets/77bcdd6f-d91e-49f3-8dde-f96a63c5e10c" />

## Author
**Anas Tarek**
