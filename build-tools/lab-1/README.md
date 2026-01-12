# Lab 1: Building and Packaging Java Applications with Gradle

## Objective
Install Gradle, clone source code, run unit tests, build and run a Java application.

## Prerequisites
- Linux/WSL environment
- Java 17 installed

## Lab Steps

### Step 1: Install Gradle
```bash
sudo apt update
sudo apt install gradle
```

### Step 2: Clone Source Code
```bash
git clone https://github.com/Ibrahim-Adel15/build1.git
cd build1
```

### Step 3: Run Unit Tests
```bash
gradle test
```

### Step 4: Build Application
```bash
gradle build
```
*This generates artifact: `build/libs/ivolve-app.jar`*

### Step 5: Run Application
```bash
java -jar build/libs/ivolve-app.jar
```

### Step 6: Verify Application
Check that the application starts successfully and produces expected output.

## Expected Output
- Unit tests pass
- Build generates `build/libs/ivolve-app.jar`
- Application runs without errors

## Author
**Anas** - iVolve Training Labs