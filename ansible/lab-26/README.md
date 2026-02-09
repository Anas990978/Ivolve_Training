# Lab 26: Initial Ansible Configuration and Ad-Hoc Execution

## Objective
Install and configure Ansible on the control node, set up SSH key-based access to a managed node, create an inventory, and run ad-hoc commands.

## Prerequisites
- Linux/WSL environment
- Ansible installed on the control node
- SSH access to the managed node (localhost used in this lab)

## Lab Steps

### Step 1: Install and Configure Ansible
Install Ansible on the control node and verify it is available:
```bash
ansible --version
```

### Step 2: Generate SSH Key on Control Node
```bash
ssh-keygen -t rsa -b 4096
```

### Step 3: Copy Public Key to Managed Node
```bash
ssh-copy-id localhost
```

### Step 4: Verify SSH Keys
```bash
ls ~/.ssh
```

### Step 5: Create Inventory
Use `inventory.ini` in this lab folder as the inventory for the managed node.

### Step 6: Run Ad-Hoc Commands
Ping the managed node:
```bash
ansible -i inventory.ini local -m ping
```

Check disk space:
```bash
ansible -i inventory.ini local -a "df -h"
```

## Expected Output
- Successful ping response from the managed node.
- Disk usage details returned by `df -h`.

## Author
**Anas Tarek**
