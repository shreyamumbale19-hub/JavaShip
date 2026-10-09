# JavaShip

### A command-line utility for Java environment diagnostics.

JavaShip helps developers inspect their Java setup, review system information, and identify basic environment configuration issues — directly from the terminal.

Built with Java and Maven, it brings common Java environment checks together in one lightweight CLI.

[Features](#features) · [See It in Action](#see-it-in-action) · [Getting Started](#getting-started) · [Usage](#usage)

---

## Features

* **Java version inspection** — Display the Java version used to run the application.
* **System information** — View the operating system, processor architecture, available processors, Java home, and other runtime details.
* **Environment checks** — Inspect Java runtime properties and `JAVA_HOME` configuration.
* **Diagnostic results** — Get readable `PASS` or `CHECK REQUIRED` statuses for supported checks.
* **Interactive CLI** — Access features through a numbered terminal menu.
* **Input validation** — Handle invalid menu choices and non-numeric input.
* **Automated tests** — Test selected components with JUnit 5.
* **Runnable JAR** — Launch the application directly from the command line.

## Tech Stack

| Technology     | Purpose                      |
| -------------- | ---------------------------- |
| Java 21        | Application logic            |
| Apache Maven   | Build and project management |
| JUnit 5        | Unit testing                 |
| Git and GitHub | Version control              |

## See It in Action

Launch JavaShip from your terminal:

```bash
java -jar target/javaship-1.0.0-SNAPSHOT.jar
```

Select option `6` to run Java installation diagnostics.

Example output from a configured Java environment:

```text
--- Java Installation Diagnostics ---
Java version available: PASS
Java home available: PASS
JAVA_HOME configured: PASS

Diagnostics completed.
```

**What do the results mean?**

* `Java version available` — The running JVM exposes its version.
* `Java home available` — The JVM reports its installation directory.
* `JAVA_HOME configured` — The environment variable is set.

A missing `JAVA_HOME` does not necessarily mean Java is unusable. It indicates a configuration worth reviewing.

## Getting Started

### Requirements

* JDK 21 or a compatible Java setup
* Apache Maven
* Git (optional, for cloning the repository)

Verify your installation:

```bash
java -version
javac -version
mvn -version
```

### Clone the repository

```bash
git clone https://github.com/shreyamumbale19-hub/JavaShip.git
cd JavaShip
```

### Run the tests

```bash
mvn clean test
```

### Build the application

```bash
mvn clean package
```

### Launch JavaShip

```bash
java -jar target/javaship-1.0.0-SNAPSHOT.jar
```

## Usage

Choose an option from the interactive menu.

| Option | Action                            |
| ------ | --------------------------------- |
| 1      | About JavaShip                    |
| 2      | Check Java version                |
| 3      | Display system information        |
| 4      | Check Java environment            |
| 5      | Exit                              |
| 6      | Run Java installation diagnostics |

## Project Structure

```text
JavaShip/
├── src/
│   ├── main/java/com/javaship/
│   │   ├── JavaShip.java
│   │   ├── JavaDiagnostics.java
│   │   ├── JavaEnvironment.java
│   │   ├── MenuHandler.java
│   │   └── SystemInfo.java
│   └── test/java/com/javaship/
├── pom.xml
└── README.md
```

## Testing

JavaShip uses JUnit 5 for automated testing.

```bash
mvn clean test
```

Package the application with:

```bash
mvn clean package
```

## Current Scope

JavaShip currently provides Java runtime information, basic environment checks, and installation diagnostics. It does not automatically repair Java installations.

### Planned Improvements

* Detect Java compiler and Maven availability from within the application
* Provide actionable troubleshooting guidance
* Validate Maven project structure
* Add safe build and test helpers for selected Java projects
* Expand diagnostic and edge-case test coverage

## Contributing

Bug reports, suggestions, and improvements are welcome. Keep contributions focused, document new functionality, and add tests where appropriate.

## License

No license has been added yet. A license should be selected before distributing JavaShip as an open-source project.

---

**Repository:** [shreyamumbale19-hub/JavaShip](https://github.com/shreyamumbale19-hub/JavaShip)
