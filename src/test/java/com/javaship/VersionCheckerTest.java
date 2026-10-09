package com.javaship;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionCheckerTest {

    @Test
    void shouldReportCompatibleVersion() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));
            VersionChecker.checkVersion(21);
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains("COMPATIBLE"));
    }

    @Test
    void shouldReportIncompatibleVersion() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try {
            System.setOut(new PrintStream(output));
            VersionChecker.checkVersion(99);
        } finally {
            System.setOut(originalOutput);
        }

        assertTrue(output.toString().contains("NOT COMPATIBLE"));
    }
}
