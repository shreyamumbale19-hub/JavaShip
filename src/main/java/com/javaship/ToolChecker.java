package com.javaship;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ToolChecker {

    public static void checkTool(String toolName, String command) {
        System.out.println("\nChecking " + toolName + "...");

        try {
            Process process = new ProcessBuilder(
                    command.equals("java") ? "java" :
                    command.equals("maven") ? "mvn" : "git",
                    "--version"
            )
                    .redirectErrorStream(true)
                    .start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String output = reader.readLine();
            int exitCode = process.waitFor();

            if (exitCode == 0 && output != null) {
                System.out.println("Status: AVAILABLE");
                System.out.println("Version: " + output);
            } else {
                System.out.println("Status: CHECK REQUIRED");
            }

        } catch (Exception e) {
            System.out.println("Status: NOT FOUND");
            System.out.println(
                    "Please check whether " + toolName
                    + " is installed and added to PATH."
            );
        }
    }

    public static void runChecks() {
        System.out.println("\n--- Development Tool Checker ---");

        checkTool("Java", "java");
        checkTool("Maven", "maven");
        checkTool("Git", "git");

        System.out.println("\nTool checks completed.");
    }
}