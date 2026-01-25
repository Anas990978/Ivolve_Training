# Lab 13: Kubernetes Persistent Volumes and Persistent Volume Claims

## Objective
Create and manage Persistent Volumes (PV) and Persistent Volume Claims (PVC) for persistent storage.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured
- Namespace `ivolve` available

## Lab Steps

### Step 1: Start Minikube
```bash
minikube start
```

### Step 2: Access Minikube Node (Optional)
```bash
minikube ssh
sudo mkdir -p /mnt/app-logs
sudo chmod 777 /mnt/app-logs
exit

```

### Step 3: Create Persistent Volume
```bash
vim pv.yaml
```

Add the following content:
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: app-logs-pv
  namespace: ivolve
spec:
  capacity:
    storage: 1Gi
  accessModes:
    - ReadWriteMany
  persistentVolumeReclaimPolicy: Retain
  hostPath:
    path: /mnt/app-logs
```

### Step 4: Apply Persistent Volume
```bash
kubectl apply -f pv.yaml
```

### Step 5: Create Persistent Volume Claim
```bash
vim pvc.yaml
```

Add the following content:
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: app-logs-pvc
  namespace: ivolve
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 1Gi
```

### Step 6: Apply Persistent Volume Claim
```bash
kubectl apply -f pvc.yaml
```

### Step 7: Verify PV and PVC Status
```bash
kubectl get pv,pvc -n ivolve
```

### Step 8: Describe Persistent Volume
```bash
kubectl describe pv app-logs-pv
```

### Step 9: Describe Persistent Volume Claim
```bash
kubectl describe pvc app-logs-pvc -n ivolve
```

## Expected Results
<img width="902" height="266" alt="image" src="https://github.com/user-attachments/assets/794386f1-a900-43d1-9887-89ed73e7ac26" />
<img width="1361" height="516" alt="image" src="https://github.com/user-attachments/assets/0836f309-dcef-4bdd-8bf4-a3c386b62b2b" />

## Author
**Anas Tarek**
