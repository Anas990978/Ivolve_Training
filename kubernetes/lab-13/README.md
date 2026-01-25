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
<img width="729" height="90" alt="image" src="https://github.com/user-attachments/assets/ca0e3e73-e9a4-4100-99e0-340d34442cae" />
<img width="1364" height="331" alt="image" src="https://github.com/user-attachments/assets/5eac58ae-1c48-4ca8-b432-a13fdf815bdb" />
<img width="872" height="352" alt="image" src="https://github.com/user-attachments/assets/55eb782e-6d9b-40e9-8130-5d54b8344493" />
<img width="1363" height="484" alt="image" src="https://github.com/user-attachments/assets/9d8a5edd-2b6f-4bd1-84cc-ee1c04a4517f" />

## Author
**Anas Tarek**
