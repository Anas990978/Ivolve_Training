# Lab 27: Automated Web Server Configuration Using Ansible Playbooks

## Objective
Automate web server configuration with Ansible: install Nginx, customize the web page, and verify the setup.

## Prerequisites
- Linux/WSL environment
- Ansible installed on the control node
- SSH access to the managed node (localhost used in this lab)

## Lab Steps

### Step 1: Prepare Inventory
Use `inventory.ini` in this lab folder for the managed node.

### Step 2: Run the Playbook
```bash
ansible-playbook -i inventory.ini webServer.yaml
```

### Step 3: Verify the Web Server
```bash
curl http://localhost
```

## Expected Output
- Playbook completes successfully (Nginx installed and page updated).
- `curl` returns the customized web page content.

## Author
**Anas Tarek**
