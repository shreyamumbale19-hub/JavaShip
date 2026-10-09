package com.javaship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SystemInfoTest {

    @Test
    void shouldReturnJavaVersion() {
        assertNotNull(System.getProperty("java.version"));
    }

    @Test
    void shouldReturnOperatingSystem() {
        assertNotNull(System.getProperty("os.name"));
    }

    @Test
    void shouldReturnArchitecture() {
        assertNotNull(System.getProperty("os.arch"));
    }

    @Test
    void shouldReturnJavaHome() {
        assertNotNull(System.getProperty("java.home"));
    }
}