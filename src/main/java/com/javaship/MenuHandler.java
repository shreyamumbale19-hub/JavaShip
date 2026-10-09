package com.javaship;

public class MenuHandler {

    public static String getMessage(int choice) {
        switch (choice) {
            case 1:
                return "JavaShip is your Java developer tool!";

            case 2:
                return "Java version: "
                        + System.getProperty("java.version");

            case 3:
                return "System information:";

            case 4:
                return "Checking Java environment...";

            case 5:
                return "Thanks for using JavaShip!";

            case 6:
                return "Running Java installation diagnostics...";

            default:
                return "Invalid option. Please try again.";
        }
    }
}