# Lab 14: Kubernetes StatefulSets with MySQL

## Objective
Deploy MySQL database using StatefulSet with persistent storage and headless service.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured
- Namespace `ivolve` available
- ConfigMap and Secret from lab-12

## Lab Steps

### Step 1: Create MySQL Headless Service
```bash
kubectl create service clusterip mysql-headless --tcp=3306:3306 --clusterip=None -n ivolve --dry-run=client -o yaml > mysql-headless-service.yaml
```

### Step 2: View Generated Service YAML
```bash
cat mysql-headless-service.yaml
```

### Step 3: Create MySQL StatefulSet
```bash
vim mysql-statefulset.yaml
```

Add the following content:
```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mysql-statefulset
  namespace: ivolve
spec:
  serviceName: mysql
  replicas: 1
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      tolerations:
      - key: "node"
        operator: "Equal"
        value: "worker"
        effect: "NoSchedule"
      containers:
      - name: mysql
        image: mysql:8
        envFrom:
        - configMapRef:
            name: mysql-config
        - secretRef:
            name: mysql-secret
        ports:
        - containerPort: 3306
        volumeMounts:
        - name: mysql-data
          mountPath: /var/lib/mysql
  volumeClaimTemplates:
  - metadata:
      name: mysql-data
    spec:
      accessModes: [ "ReadWriteOnce" ]
      resources:
        requests:
          storage: 1Gi
```

### Step 4: Apply Headless Service
```bash
kubectl apply -f mysql-headless-service.yaml
```

### Step 5: Apply StatefulSet
```bash
kubectl apply -f mysql-statefulset.yaml
```

### Step 6: Remove Node Taint (if needed)
```bash
kubectl taint node minikube node=worker:NoSchedule-
```

### Step 7: Verify Deployment
```bash
kubectl get all -n ivolve
kubectl get statefulsets.apps -n ivolve
kubectl get pods -n ivolve
```

### Step 8: Check PVC Creation
```bash
kubectl get pvc -n ivolve
```

### Step 9: Connect to MySQL Database
```bash
kubectl exec -it mysql-statefulset-0 -n ivolve -- mysql -u anas -panas123
```

## Expected Results
<img width="902" height="266" alt="image" src="https://github.com/user-attachments/assets/794386f1-a900-43d1-9887-89ed73e7ac26" />
<img width="1361" height="516" alt="image" src="https://github.com/user-attachments/assets/0836f309-dcef-4bdd-8bf4-a3c386b62b2b" />

## Author
**Anas Tarek**
