# Lab 2: Building and Packaging Java Applications with Maven

## Objective
Install Maven, clone source code, run unit tests, build and run a Java application.

## Prerequisites
- Linux/WSL environment
- Java 17 installed

## Lab Steps

### Step 1: Install Maven
```bash
sudo apt update
sudo apt install maven
```

### Step 2: Clone Source Code
```bash
git clone https://github.com/Ibrahim-Adel15/build2.git
cd build2
```

### Step 3: Run Unit Tests
```bash
mvn test
```

### Step 4: Build Application
```bash
mvn package
```
*This generates artifact: `target/hello-ivolve-1.0-SNAPSHOT.jar`*

### Step 5: Run Application
```bash
java -jar target/hello-ivolve-1.0-SNAPSHOT.jar
```

### Step 6: Verify Application
Check that the application starts successfully and produces expected output.

## Expected Output
<img width="1411" height="760" alt="image" src="https://github.com/user-attachments/assets/0a3e99ae-9813-4200-b7ec-bb724a75eff0" />
<img width="1369" height="697" alt="image" src="https://github.com/user-attachments/assets/46b8c7ea-ac18-43bd-8250-03263695deef" />


## Author
**Anas Tarek** 
