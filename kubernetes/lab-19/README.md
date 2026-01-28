# Lab 19: Node-Wide Pod Management with DaemonSet

## Objective
Deploy a DaemonSet for Prometheus node-exporter that runs on every node and tolerates all existing taints.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-19
cd lab-19
```

### Step 2: Create Monitoring Namespace
```bash
kubectl create ns monitoring
kubens monitoring
```

### Step 3: Create DaemonSet YAML
```bash
vim node-exporter-daemonset.yaml
```

Add the following content:
```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: node-exporter
  namespace: monitoring
  labels:
    app: node-exporter
spec:
  selector:
    matchLabels:
      app: node-exporter
  template:
    metadata:
      labels:
        app: node-exporter
    spec:
      tolerations:
      - operator: Exists
      hostNetwork: true
      hostPID: true
      containers:
      - name: node-exporter
        image: prom/node-exporter:latest
        ports:
        - containerPort: 9100
          hostPort: 9100
        volumeMounts:
        - name: proc
          mountPath: /host/proc
          readOnly: true
        - name: sys
          mountPath: /host/sys
          readOnly: true
        - name: root
          mountPath: /rootfs
          readOnly: true
        args:
        - '--path.procfs=/host/proc'
        - '--path.sysfs=/host/sys'
        - '--collector.filesystem.ignored-mount-points=^/(sys|proc|dev|host|etc)($|/)'
      volumes:
      - name: proc
        hostPath:
          path: /proc
      - name: sys
        hostPath:
          path: /sys
      - name: root
        hostPath:
          path: /
```

### Step 4: Apply DaemonSet
```bash
kubectl apply -f node-exporter-daemonset.yaml
```

### Step 5: Verify DaemonSet Deployment
```bash
kubectl get pods -n monitoring -o wide
kubectl get daemonsets.apps -n monitoring -o wide
```

### Step 6: Validate Node Coverage
```bash
kubectl get nodes
```

### Step 7: Confirm Metrics Exposure
```bash
curl http://192.168.58.2:9100/metrics
```

## Expected Results
<img width="1363" height="635" alt="image" src="https://github.com/user-attachments/assets/595e6827-0899-4ca1-aaa6-fba941a00590" />

## Author
**Anas Tarek**
