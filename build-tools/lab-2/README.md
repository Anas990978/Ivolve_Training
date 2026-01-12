# Lab 2 — Building and Testing a Java Application with Maven

## Objective
Build, test and run a Java application using Maven build tool.

## Prerequisites
- Linux/WSL environment
- Java 17 installed
- Maven installed

## Lab Steps

### Step 1: Navigate to Project Directory
```bash
cd build-tools/lab-2/build2
```

### Step 2: Build and Package the Application
```bash
mvn clean package
```

### Step 3: Run Tests
```bash
mvn test
```

### Step 4: Run the Application
```bash
java -jar target/*.jar
```

## Alternative: Using Docker
If you prefer to run Maven in a container:
```bash
docker run --rm -v "$PWD":/workspace -w /workspace maven:3.8.6-openjdk-17 mvn clean package
```

## Expected Output
- Successful build with no errors
- All tests pass
- JAR file created in `target/` directory
- Application runs and displays output

## Notes
- Build artifacts are generated in the `target/` directory
- Test reports are available in `target/surefire-reports/`
- The `pom.xml` file contains project configuration and dependencies

## Author
**Anas** - iVolve Training Labs