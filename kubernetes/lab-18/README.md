# Lab 18: Control Pod-to-Pod Traffic via Network Policy

## Objective
Define a NetworkPolicy resource to control pod-to-pod traffic and restrict access to MySQL database.

## Prerequisites
- Kubernetes cluster running (Minikube)
- kubectl configured
- Namespace `ivolve` available
- MySQL StatefulSet from lab-14
- Node.js Deployment from lab-15/16

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-18
cd lab-18
```

### Step 2: Create NetworkPolicy YAML
```bash
vim network-policy.yaml
```

Add the following content:
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-app-to-mysql
  namespace: ivolve
spec:
  podSelector:
    matchLabels:
      app: mysql
  policyTypes:
  - Ingress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: nodejs-app
    ports:
    - protocol: TCP
      port: 3306
```

### Step 3: View NetworkPolicy Configuration
```bash
cat network-policy.yaml
```

### Step 4: Apply NetworkPolicy
```bash
kubectl apply -f network-policy.yaml
```

### Step 5: Verify NetworkPolicy Creation
```bash
kubectl get networkpolicies -n ivolve
kubectl describe networkpolicy allow-app-to-mysql -n ivolve
```

## Expected Results
<img width="1036" height="585" alt="image" src="https://github.com/user-attachments/assets/a95af68e-b9b0-470b-804c-37b1991eedf9" />
<img width="1169" height="255" alt="image" src="https://github.com/user-attachments/assets/b1932895-4a32-405a-beb3-3c19e548d33f" />

## Author
**Anas Tarek**
