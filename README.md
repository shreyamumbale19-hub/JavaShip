# JavaShip

### A command-line utility for Java environment diagnostics.

JavaShip is a Java-based command-line tool that helps developers inspect their Java environment and view essential system information.

## Features

* **Java Version:** Check the Java version currently running.
* **System Information:** Display the operating system, architecture, processor count, and Java home.
* **Environment Check:** View Java configuration and `JAVA_HOME` status.
* **Installation Diagnostics:** Check whether essential Java environment properties are available.
* **Interactive Menu:** Access features through a simple command-line interface.
* **Input Validation:** Handle invalid menu input.
* **Unit Testing:** Test core functionality using JUnit 5.
* **Executable JAR:** Build and run the application using Maven.

## Tech Stack

* Java 21
* Maven
* JUnit 5
* Git and GitHub

## Getting Started

### Prerequisites

* JDK 21 or a compatible JDK
* Apache Maven
* Git

### Clone the repository

```bash
git clone https://github.com/shreyamumbale19-hub/JavaShip.git
cd JavaShip
```

### Run the tests

```bash
mvn clean test
```

### Build the project

```bash
mvn clean package
```

### Run JavaShip

```bash
java -jar target/javaship-1.0.0-SNAPSHOT.jar
```

## Usage

Choose an option from the interactive menu:

| Option | Feature                           |
| ------ | --------------------------------- |
| 1      | About JavaShip                    |
| 2      | Check Java version                |
| 3      | Display system information        |
| 4      | Check Java environment            |
| 5      | Exit                              |
| 6      | Run Java installation diagnostics |

### Example: Installation Diagnostics

```text
--- Java Installation Diagnostics ---
Java version available: PASS
Java home available: PASS
JAVA_HOME configured: PASS

Diagnostics completed.
```

The `JAVA_HOME` status depends on your local environment, so it may show `CHECK REQUIRED` if it is not configured.

## Project Structure

```text
JavaShip/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/java/com/javaship/
    │   ├── JavaShip.java
    │   ├── JavaDiagnostics.java
    │   ├── JavaEnvironment.java
    │   ├── MenuHandler.java
    │   └── SystemInfo.java
    └── test/java/com/javaship/
        ├── JavaEnvironmentTest.java
        ├── MenuHandlerTest.java
        └── SystemInfoTest.java
```

## Future Improvements

* Check whether Java compiler and Maven are available.
* Add common Java environment troubleshooting suggestions.
* Expand unit test coverage.
* Add more developer utilities.

## Author

Developed as a personal Java project to practice command-line application development, environment checks, and unit testing.

GitHub: [shreyamumbale19-hub](https://github.com/shreyamumbale19-hub)
