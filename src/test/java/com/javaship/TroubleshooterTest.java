package com.javaship;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TroubleshooterTest {

    @Test
    void shouldDisplayTroubleshootingReport() {
        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));

            Troubleshooter.showSuggestions();

        } finally {
            System.setOut(originalOutput);
        }

        String report = output.toString();

        assertTrue(report.contains("JavaShip Troubleshooting"));
        assertTrue(report.contains("Active Java version"));
        assertTrue(report.contains("Java compiler"));
        assertTrue(report.contains("Troubleshooting check completed"));
    }
}