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

### Step 6: Verify Deployment
```bash
kubectl get all -n ivolve
kubectl get statefulsets.apps -n ivolve
kubectl get pods -n ivolve
```

### Step 7: Connect to MySQL Database as user
```bash
kubectl exec -it mysql-statefulset-0 -n ivolve -- mysql -u anas -panas123
```
### Step 8: Connect to MySQL Database as root
```bash
kubectl exec -it mysql-statefulset-0 -n ivolve -- mysql -u root -proot123
```

## Expected Results
<img width="887" height="79" alt="image" src="https://github.com/user-attachments/assets/d8432df9-c925-489d-bfda-d9030a3fce86" />
<img width="1359" height="499" alt="image" src="https://github.com/user-attachments/assets/fcd392b8-a02c-4a0b-a7c7-9af2f43cad0b" />
<img width="792" height="252" alt="image" src="https://github.com/user-attachments/assets/9a722ced-5ef6-4b4c-869e-6a966d443d0a" />
<img width="1096" height="294" alt="image" src="https://github.com/user-attachments/assets/ca0af6f6-f7d7-44ea-bb5a-a1ccd18d3fab" />
<img width="1128" height="284" alt="image" src="https://github.com/user-attachments/assets/a0db6325-c0f1-463c-a583-873f529db99d" />


## Author
**Anas Tarek**
