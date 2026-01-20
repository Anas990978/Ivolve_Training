# Lab 10: Kubernetes Node Taints

## Objective
Configure Kubernetes node taints to control pod scheduling.

## Prerequisites
- Kubernetes cluster (Minikube) running
- kubectl configured

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-10
cd lab-10
```

### Step 2: Check Cluster Nodes
```bash
kubectl get nodes
```

### Step 3: Apply Taint to Node
```bash
kubectl taint node minikube node=worker:NoSchedule
```

### Step 4: Verify Taint Applied
```bash
kubectl describe nodes minikube | grep Taints
```

## Expected Results
<img width="1158" height="274" alt="image" src="https://github.com/user-attachments/assets/7b129ac1-4fe4-4051-8ca3-9bdaa458f3bf" />

## Author
**Anas Tarek** 
