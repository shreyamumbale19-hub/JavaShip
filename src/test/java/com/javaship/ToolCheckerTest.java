package com.javaship;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ToolCheckerTest {

    @Test
    void shouldDisplayDevelopmentToolChecks() {
        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));

            ToolChecker.runChecks();

        } finally {
            System.setOut(originalOutput);
        }

        String report = output.toString();

        assertTrue(report.contains("Development Tool Checker"));
        assertTrue(report.contains("Checking Java"));
        assertTrue(report.contains("Checking Maven"));
        assertTrue(report.contains("Checking Git"));
        assertTrue(report.contains("Tool checks completed"));
    }
}
