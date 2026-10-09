package com.javaship;

public class JavaDiagnostics {

    public static boolean isJavaVersionAvailable() {
        return System.getProperty("java.version") != null;
    }

    public static boolean isJavaHomeAvailable() {
        return System.getProperty("java.home") != null;
    }

    public static boolean isJavaHomeConfigured() {
        String javaHome = System.getenv("JAVA_HOME");
        return javaHome != null && !javaHome.isBlank();
    }

    public static void runDiagnostics() {
        System.out.println("\n--- Java Installation Diagnostics ---");

        printStatus(
                "Java version available",
                isJavaVersionAvailable()
        );

        printStatus(
                "Java home available",
                isJavaHomeAvailable()
        );

        printStatus(
                "JAVA_HOME configured",
                isJavaHomeConfigured()
        );

        System.out.println("\nDiagnostics completed.");
    }

    private static void printStatus(String label, boolean passed) {
        System.out.println(
                label + ": " + (passed ? "PASS" : "CHECK REQUIRED")
        );
    }
}