# Lab 11: Kubernetes Namespace and Resource Quotas

## Objective
Create a namespace and implement resource quotas to limit pod creation.

## Prerequisites
- Kubernetes cluster running
- kubectl configured

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-11
cd lab-11
```

### Step 2: Create Namespace
```bash
kubectl create ns ivolve
```

### Step 3: Switch to New Namespace
```bash
kubens ivolve
```

### Step 4: Check Available Namespaces
```bash
kubectl get ns
```

### Step 5: Create Resource Quota YAML
```bash
kubectl create quota lab-11-quota -n ivolve --hard pods=2 --dry-run=client -o yaml > lab11.yaml
```

### Step 6: View Generated YAML
```bash
cat lab11.yaml
```

### Step 7: Apply Resource Quota
```bash
kubectl apply -f lab11.yaml
```

### Step 8: Verify Resource Quota
```bash
kubectl get resourcequotas -n ivolve
```

### Step 9: Test Pod Creation Within Quota
```bash
kubectl run test1 --image=nginx -n ivolve
kubectl run test2 --image=nginx -n ivolve
```

### Step 10: Test Pod Creation Exceeding Quota
```bash
kubectl run test3 --image=nginx -n ivolve
```

### Step 11: Check Pod Status
```bash
kubectl get pods -n ivolve
```

## Expected Results
<img width="902" height="266" alt="image" src="https://github.com/user-attachments/assets/794386f1-a900-43d1-9887-89ed73e7ac26" />
<img width="1361" height="516" alt="image" src="https://github.com/user-attachments/assets/0836f309-dcef-4bdd-8bf4-a3c386b62b2b" />

## Author
**Anas Tarek**
