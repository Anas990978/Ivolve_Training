# Lab 12: Kubernetes ConfigMaps and Secrets

## Objective
Create and manage ConfigMaps and Secrets for MySQL database configuration.

## Prerequisites
- Kubernetes cluster running
- kubectl configured
- Namespace `ivolve` available

## Lab Steps

### Step 1: Create Lab Directory
```bash
mkdir lab-12
cd lab-12
```

### Step 2: Create MySQL ConfigMap
```bash
vim mysql-configmap.yaml
```

Add the following content:
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: mysql-config
  namespace: ivolve
data:
  MYSQL_DATABASE: "mysql"
  MYSQL_USER: "anas123"
```

### Step 3: Apply ConfigMap
```bash
kubectl apply -f mysql-configmap.yaml
```

### Step 4: Verify ConfigMap Creation
```bash
kubectl get configmaps
```

### Step 5: Encode Passwords to Base64
```bash
echo -n anas123 | base64
echo -n root123 | base64
```

### Step 6: Create MySQL Secret
```bash
vim secrets.yaml
```

Add the following content:
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: mysql-secret
  namespace: ivolve
type: Opaque
data:
  MYSQL_ROOT_PASSWORD: cm9vdDEyMw==
  MYSQL_PASSWORD: YW5hczEyMw==
```

### Step 7: Apply Secret
```bash
kubectl apply -f secrets.yaml
```

### Step 8: Describe ConfigMap
```bash
kubectl describe configmaps mysql-config
```

### Step 9: Describe Secret
```bash
kubectl describe secrets mysql-secret
```


## Expected Results
<img width="1183" height="628" alt="image" src="https://github.com/user-attachments/assets/4012dac6-5f42-4898-9143-afc88ff375a8" />


## Author
**Anas Tarek** 
