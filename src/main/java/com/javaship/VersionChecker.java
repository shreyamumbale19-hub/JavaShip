package com.javaship;

public class VersionChecker {

    public static void checkVersion(int requiredVersion) {
        String currentVersion = System.getProperty("java.specification.version");

        try {
            int installedVersion = Integer.parseInt(currentVersion);

            System.out.println("\n--- Java Version Compatibility Checker ---");
            System.out.println("Installed Java version: " + installedVersion);
            System.out.println("Required Java version: " + requiredVersion);

            if (installedVersion >= requiredVersion) {
                System.out.println("Status: COMPATIBLE");
                System.out.println("Your Java version meets the requirement.");
            } else {
                System.out.println("Status: NOT COMPATIBLE");
                System.out.println("Please install Java " + requiredVersion
                        + " or a newer version.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Could not determine the installed Java version.");
        }
    }
}