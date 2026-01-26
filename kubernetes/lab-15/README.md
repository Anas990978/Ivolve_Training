# Lab 15: Node.js Application Deployment with ClusterIP Service

## Objective
Deploy a Node.js application using Deployment with persistent storage and ClusterIP service for load balancing.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured
- Namespace `ivolve` available
- ConfigMap and Secret from lab-12
- PersistentVolume from lab-13
- Private Docker Hub image

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-15
cd lab-15
```

### Step 2: Login to Docker Hub
```bash
docker login -u anas587
```

### Step 3: Create Docker Registry Secret
```bash
kubectl create secret docker-registry dockerhub-secret \
  --docker-server=https://index.docker.io/v1/ \
  --docker-username=user_name \
  --docker-password=password \
  --docker-email=email \
  -n ivolve
```

### Step 4: Create PersistentVolumeClaim
```bash
cd ../lab-13
kubectl apply -f pvc.yaml
cd ../lab-15
```

### Step 5: Generate Deployment YAML using dry-run
```bash
kubectl create deployment nodejs-app --image=anas587/kubernets-app-app --replicas=2 -n ivolve --dry-run=client -o yaml > nodejs-deployment.yaml
```

### Step 6: Edit Deployment Configuration
```bash
vim nodejs-deployment.yaml
```

Add the following content:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: nodejs-app
  name: nodejs-app
  namespace: ivolve
spec:
  replicas: 2
  selector:
    matchLabels:
      app: nodejs-app
  template:
    metadata:
      labels:
        app: nodejs-app
    spec:
      imagePullSecrets:
      - name: dockerhub-secret
      tolerations:
      - key: "node"
        operator: "Equal"
        value: "worker"
        effect: "NoSchedule"
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
      volumes:
      - name: app-storage
        persistentVolumeClaim:
          claimName: app-logs-pvc
```

### Step 7: Generate ClusterIP Service using dry-run
```bash
kubectl create service clusterip nodejs-service --tcp=80:3000 -n ivolve --dry-run=client -o yaml > nodejs-service.yaml
```

### Step 8: Edit Service Selector
```bash
vim nodejs-service.yaml
```

Add the following content:
```yaml
apiVersion: v1
kind: Service
metadata:
  labels:
    app: nodejs-service
  name: nodejs-service
  namespace: ivolve
spec:
  ports:
  - name: 80-3000
    port: 80
    protocol: TCP
    targetPort: 3000
  selector:
    app: nodejs-app
  type: ClusterIP
```

### Step 9: Apply Deployment and Service
```bash
kubectl apply -f nodejs-deployment.yaml
kubectl apply -f nodejs-service.yaml
```

### Step 10: Verify Deployment
```bash
kubectl get deployments,pods,svc -n ivolve
kubectl get pvc -n ivolve
```

## Expected Results
<img width="1359" height="170" alt="image" src="https://github.com/user-attachments/assets/d090889f-8a03-4de2-a0ec-8cf1b4742e01" />


## Author
**Anas Tarek**
