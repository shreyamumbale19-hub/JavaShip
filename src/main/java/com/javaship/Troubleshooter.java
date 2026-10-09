package com.javaship;

import java.io.File;

public class Troubleshooter {

    public static void showSuggestions() {
        System.out.println("\n--- JavaShip Troubleshooting ---");

        checkJavaHome();
        checkJavaVersion();
        checkJavaCompiler();
        checkPath();

        System.out.println("\nTroubleshooting check completed.");
    }

    private static void checkJavaHome() {
        String javaHome = System.getenv("JAVA_HOME");

        if (javaHome == null || javaHome.isBlank()) {
            System.out.println("\nProblem: JAVA_HOME is not configured.");
            System.out.println("Suggestion: Set JAVA_HOME to your JDK folder.");
            return;
        }

        System.out.println("\nJAVA_HOME: " + javaHome);

        File compiler = new File(javaHome, "bin/javac.exe");

        if (compiler.exists()) {
            System.out.println("JDK folder check: PASSED");
        } else {
            System.out.println("Problem: javac.exe was not found in JAVA_HOME.");
            System.out.println("Suggestion: Check that JAVA_HOME points to your JDK folder.");
        }
    }

    private static void checkJavaVersion() {
        String version = System.getProperty("java.version");
        String javaHome = System.getenv("JAVA_HOME");
        String activeJavaHome = System.getProperty("java.home");

        System.out.println("\nActive Java version: " + version);
        System.out.println("Active Java location: " + activeJavaHome);

        if (javaHome != null && !javaHome.isBlank()) {
            String normalizedJavaHome = new File(javaHome)
                    .getAbsolutePath();
            String normalizedActiveHome = new File(activeJavaHome)
                    .getAbsolutePath();

            if (normalizedActiveHome.equalsIgnoreCase(normalizedJavaHome)
                    || normalizedActiveHome.equalsIgnoreCase(
                            new File(normalizedJavaHome, "jre")
                                    .getAbsolutePath())) {
                System.out.println("Java configuration: Looks consistent.");
            } else {
                System.out.println("Warning: JAVA_HOME and active Java locations differ.");
                System.out.println("Suggestion: Check which JDK your terminal uses.");
            }
        }
    }

    private static void checkJavaCompiler() {
        try {
            Process process = new ProcessBuilder("javac", "-version")
                    .redirectErrorStream(true)
                    .start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("\nJava compiler (javac): AVAILABLE");
            } else {
                System.out.println("\nProblem: Java compiler check failed.");
                System.out.println("Suggestion: Check your JDK installation and PATH.");
            }
        } catch (Exception e) {
            System.out.println("\nProblem: Java compiler (javac) was not found.");
            System.out.println("Suggestion: Add your JDK bin folder to PATH.");
        }
    }

    private static void checkPath() {
        String path = System.getenv("PATH");

        if (path == null || path.isBlank()) {
            System.out.println("\nProblem: PATH is unavailable.");
            System.out.println("Suggestion: Check your system environment variables.");
        } else {
            System.out.println("\nPATH environment variable: AVAILABLE");
        }
    }
}