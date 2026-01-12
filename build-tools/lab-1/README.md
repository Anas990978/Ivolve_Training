# Lab 1 — Building and Testing a Java Application with Gradle

## Objective
Build, test and run a Java application using Gradle build tool.

## Prerequisites
- Linux/WSL environment
- Java 17 installed
- Gradle (or use the wrapper)

## Lab Steps

### Step 1: Navigate to Project Directory
```bash
cd build-tools/lab-1/build1
```

### Step 2: Build the Application
```bash
./gradlew clean build
```
*Or if gradlew is not available:*
```bash
gradle clean build
```

### Step 3: Run Tests
```bash
./gradlew test
```

### Step 4: Run the Application
```bash
java -jar build/libs/*.jar
```

## Expected Output
- Successful build with no errors
- All tests pass
- Application runs and displays output

## Notes
- The Gradle wrapper (`./gradlew`) is preferred as it ensures consistent Gradle version
- Build artifacts are generated in the `build/` directory
- Test reports are available in `build/reports/tests/`

## Author
**Anas** - iVolve Training Labs