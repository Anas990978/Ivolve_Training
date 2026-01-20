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
- Node `minikube` has taint `node=worker:NoSchedule` applied
- Pods without toleration cannot be scheduled on tainted node

## Notes
- Taints prevent pods from being scheduled on nodes unless they have matching tolerations
- NoSchedule taint prevents new pods from being scheduled but doesn't affect existing pods

## Author
**Anas** - iVolve Training Labs