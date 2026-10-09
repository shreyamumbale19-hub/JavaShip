package com.javaship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuHandlerTest {

    @Test
    void shouldReturnAboutMessage() {
        assertEquals(
                "JavaShip is your Java developer tool!",
                MenuHandler.getMessage(1)
        );
    }

    @Test
    void shouldReturnExitMessage() {
        assertEquals(
                "Thanks for using JavaShip!",
                MenuHandler.getMessage(5)
        );
    }

    @Test
    void shouldHandleInvalidChoice() {
        assertEquals(
                "Invalid option. Please try again.",
                MenuHandler.getMessage(9)
        );
    }
}