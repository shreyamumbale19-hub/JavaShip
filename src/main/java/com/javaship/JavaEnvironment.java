package com.javaship;

public class JavaEnvironment {

    public static void checkEnvironment() {
        System.out.println("\n--- Java Environment Check ---");

        printProperty("Java Version", "java.version");
        printProperty("Java Vendor", "java.vendor");
        printProperty("Java Home", "java.home");

        String javaHome = System.getenv("JAVA_HOME");

        if (javaHome == null || javaHome.isBlank()) {
            System.out.println("JAVA_HOME: Not configured");
        } else {
            System.out.println("JAVA_HOME: " + javaHome);
        }
    }

    private static void printProperty(String label, String property) {
        String value = System.getProperty(property);
        System.out.println(label + ": " + value);
    }
}