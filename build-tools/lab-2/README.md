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
- Unit tests pass
- Build generates `target/hello-ivolve-1.0-SNAPSHOT.jar`
- Application runs without errors

## Author
**Anas** - iVolve Training Labs