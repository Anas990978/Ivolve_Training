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

## Expected Output<img width="1137" height="411" alt="image" src="https://github.com/user-attachments/assets/38aefa48-1451-41dc-ad49-35dfcd177159" />



## Author
**Anas Tarek**
