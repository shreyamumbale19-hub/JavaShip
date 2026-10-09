package com.javaship;

import java.util.Scanner;

public class JavaShip {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {

            displayMenu();
            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {

                choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        System.out.println(MenuHandler.getMessage(1));
                        break;

                    case 2:
                        System.out.println(MenuHandler.getMessage(2));
                        break;

                    case 3:
                        System.out.println(MenuHandler.getMessage(3));
                        SystemInfo.displayAll();
                        break;

                    case 4:
                        System.out.println(MenuHandler.getMessage(4));
                        JavaEnvironment.checkEnvironment();
                        break;

                    case 5:
                        System.out.println(MenuHandler.getMessage(5));
                        continue;

                    case 6:
                        System.out.println(MenuHandler.getMessage(6));
                        JavaDiagnostics.runDiagnostics();
                        break;

                    case 7:
                        System.out.println("Checking development tools...");
                        ToolChecker.runChecks();
                        break;

                    case 8:
                        Troubleshooter.showSuggestions();
                        break;

                    case 9:
                        System.out.print(
                                "Enter the required Java version (e.g., 21): "
                        );

                        if (scanner.hasNextInt()) {
                            int requiredVersion = scanner.nextInt();

                            if (requiredVersion > 0) {
                                VersionChecker.checkVersion(requiredVersion);
                            } else {
                                System.out.println(
                                        "Please enter a positive Java version."
                                );
                            }
                        } else {
                            System.out.println(
                                    "Invalid input. Please enter a number."
                            );
                            scanner.next();
                        }
                        break;

                    default:
                        System.out.println(
                                "Invalid option. Please try again."
                        );
                }

                if (choice != 5) {
                    System.out.println(
                            "\nPress Enter to return to the menu..."
                    );

                    scanner.nextLine();
                    scanner.nextLine();
                }

            } else {
                System.out.println(
                        "Invalid input! Please enter a number."
                );
                scanner.next();
            }
        }

        scanner.close();
    }

    public static void displayMenu() {

        System.out.println("\n================================");
        System.out.println("          JAVASHIP");
        System.out.println("================================");
        System.out.println("1. About JavaShip");
        System.out.println("2. Check Java version");
        System.out.println("3. System information");
        System.out.println("4. Java environment check");
        System.out.println("5. Exit");
        System.out.println("6. Java installation diagnostics");
        System.out.println("7. Check development tools");
        System.out.println("8. Troubleshooting suggestions");
        System.out.println("9. Java version compatibility checker");
    }
}