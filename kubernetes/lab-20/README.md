# Lab 20: Securing Kubernetes with RBAC and Service Accounts

## Objective
Create a ServiceAccount with RBAC permissions to demonstrate role-based access control in Kubernetes.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured
- Namespace `ivolve` available

## Lab Steps

### Step 1: Create Lab Directory
```bash
cd lab-20
kubens ivolve
```

### Step 2: Generate ServiceAccount YAML using dry-run
```bash
kubectl create serviceaccount jenkins-sa -n ivolve --dry-run=client -o yaml > jenkins-sa.yaml
cat jenkins-sa.yaml
```

### Step 3: Apply ServiceAccount
```bash
kubectl apply -f jenkins-sa.yaml
kubectl get serviceaccounts -n ivolve
```

### Step 4: Create Secret for ServiceAccount Token
```bash
vim sa-secret.yaml
```

Add the following content:
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: jenkins-sa-token
  namespace: ivolve
  annotations:
    kubernetes.io/service-account.name: jenkins-sa
type: kubernetes.io/service-account-token
```

### Step 5: Apply Secret
```bash
kubectl apply -f sa-secret.yaml
kubectl get secrets -n ivolve
```

### Step 6: Generate Role YAML using dry-run
```bash
kubectl create role pod-reader --verb=get,list --resource=pods -n ivolve --dry-run=client -o yaml > pod-reader-role.yaml
kubectl apply -f pod-reader-role.yaml
```

### Step 7: Generate RoleBinding YAML using dry-run
```bash
kubectl create rolebinding jenkins-sa-binding --role=pod-reader --serviceaccount=ivolve:jenkins-sa -n ivolve --dry-run=client -o yaml > jenkins-sa-rolebinding.yaml
kubectl apply -f jenkins-sa-rolebinding.yaml
```

### Step 8: View Generated Files
```bash
cat jenkins-sa-rolebinding.yaml
cat pod-reader-role.yaml
```
### Step 9: Validate RBAC Permissions
```bash
# Test allowed operations (should return "yes")
kubectl auth can-i get pods --as=system:serviceaccount:ivolve:jenkins-sa -n ivolve
kubectl auth can-i list pods --as=system:serviceaccount:ivolve:jenkins-sa -n ivolve

# Test forbidden operation (should return "no")
kubectl auth can-i delete pods --as=system:serviceaccount:ivolve:jenkins-sa -n ivolve
```

## Expected Results
<img width="1361" height="609" alt="image" src="https://github.com/user-attachments/assets/5e2a4222-f31b-4e7e-9059-c8ce11456e9e" />
<img width="1351" height="648" alt="image" src="https://github.com/user-attachments/assets/ba090647-e5c3-4bec-8b5c-998cbd2caadf" />
<img width="1239" height="101" alt="image" src="https://github.com/user-attachments/assets/f197c6cc-19a4-4d81-bb44-98edae2265d0" />

## Author
**Anas Tarek**
