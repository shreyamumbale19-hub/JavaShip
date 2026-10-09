package com.javaship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JavaEnvironmentTest {

    @Test
    void shouldHaveJavaVersion() {
        assertNotNull(System.getProperty("java.version"));
    }

    @Test
    void shouldHaveJavaHome() {
        assertNotNull(System.getProperty("java.home"));
    }
}