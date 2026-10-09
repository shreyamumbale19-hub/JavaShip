# JavaShip

### Java-Based Environment Diagnostic CLI

I built JavaShip as a hands-on Java project to understand how Java applications can interact with the system, check development tools, and help identify common environment configuration issues.

While developing this project, I worked with Java, Maven, JUnit, and Git. I implemented a menu-driven command-line application and gradually added features for Java diagnostics, tool verification, troubleshooting, and version compatibility.

## What I Implemented

* Created an interactive command-line menu to access different features.
* Added Java version and system information checks.
* Implemented Java environment verification and installation diagnostics.
* Developed a tool checker to verify the availability of Java, Maven, and Git.
* Built a troubleshooting feature to inspect `JAVA_HOME`, the active Java installation, compiler availability, and `PATH`.
* Implemented a Java version compatibility checker to compare the installed Java version with a required version.
* Added automated tests using JUnit 5 for the troubleshooting, tool-checking, and version compatibility features.

## Technologies Used

* **Java** — Core application logic and object-oriented programming.
* **Maven** — Project configuration, dependency management, and builds.
* **JUnit 5** — Automated testing.
* **Git and GitHub** — Version control and project hosting.

## Running the Project

### Prerequisites

* JDK 21 or a compatible newer JDK
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

### Build the application

```bash
mvn clean package
```

### Run JavaShip

```bash
java -jar target/javaship-1.0.0-SNAPSHOT.jar
```

After launching the application, select an option from the menu to use the available features.

## What I Learned

Working on JavaShip helped me practise Java programming, organize code into separate classes, execute system commands from Java, inspect environment variables, use Maven to build a project, and write automated tests with JUnit.

It also helped me understand how to develop a project incrementally, test new features, and maintain the code using Git and GitHub.

## Future Improvements

* Improve diagnostic accuracy and error handling.
* Add more automated tests for edge cases.
* Provide more detailed troubleshooting suggestions.
* Expand the environment checks with additional development tools.

---

**JavaShip is an ongoing learning project that I am building to strengthen my Java development and debugging skills.**
