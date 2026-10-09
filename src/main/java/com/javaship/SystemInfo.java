package com.javaship;

public class SystemInfo {

    public static void showJavaVersion() {
        System.out.println(
                "Java version: " + System.getProperty("java.version")
        );
    }

    public static void showOperatingSystem() {
        System.out.println(
                "Operating system: " + System.getProperty("os.name")
        );
    }

    public static void showArchitecture() {
        System.out.println(
                "Architecture: " + System.getProperty("os.arch")
        );
    }

    public static void showProcessors() {
        System.out.println(
                "Available processors: "
                        + Runtime.getRuntime().availableProcessors()
        );
    }

    public static void showJavaHome() {
        System.out.println(
                "Java home: " + System.getProperty("java.home")
        );
    }

    public static void showUserName() {
        System.out.println(
                "User name: " + System.getProperty("user.name")
        );
    }

    public static void displayAll() {
        System.out.println("\n--- System Information ---");
        showJavaVersion();
        showOperatingSystem();
        showArchitecture();
        showProcessors();
        showJavaHome();
        showUserName();
    }
}