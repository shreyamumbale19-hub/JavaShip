package com.javaship;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CheckResultTest {

    @Test
    void shouldCreateSuccessfulResult() {
        CheckResult result = new CheckResult(
                "Java installation",
                CheckResult.Status.PASS,
                "Java 21 is available."
        );

        assertEquals("Java installation", result.name());
        assertEquals(CheckResult.Status.PASS, result.status());
        assertEquals("Java 21 is available.", result.details());
        assertEquals(
                CheckResult.Category.GENERAL,
                result.category()
        );
        assertEquals(
                CheckResult.Severity.INFO,
                result.severity()
        );
        assertTrue(result.isSuccessful());
        assertFalse(result.isFailure());
        assertFalse(result.isWarning());
        assertFalse(result.isBlocking());
    }

    @Test
    void shouldCreateAdvancedFailureResult() {
        CheckResult result = CheckResult.fail(
                "Maven build",
                CheckResult.Category.BUILD,
                CheckResult.Severity.HIGH,
                "Compilation failed.",
                "BUILD_FAILED",
                1250
        );

        assertEquals(CheckResult.Status.FAIL, result.status());
        assertEquals(CheckResult.Category.BUILD, result.category());
        assertEquals(CheckResult.Severity.HIGH, result.severity());
        assertEquals("BUILD_FAILED", result.errorCode());
        assertEquals(1250, result.durationMillis());

        assertTrue(result.isFailure());
        assertTrue(result.isBlocking());
        assertFalse(result.isSuccessful());
    }

    @Test
    void shouldCreateWarningResult() {
        CheckResult result = CheckResult.warn(
                "README documentation",
                CheckResult.Category.DOCUMENTATION,
                "Documentation needs review.",
                50
        );

        assertEquals(CheckResult.Status.WARN, result.status());
        assertEquals(
                CheckResult.Category.DOCUMENTATION,
                result.category()
        );
        assertEquals(
                CheckResult.Severity.MEDIUM,
                result.severity()
        );
        assertTrue(result.isWarning());
        assertFalse(result.isBlocking());
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CheckResult(
                        " ",
                        CheckResult.Status.FAIL,
                        "Invalid name."
                )
        );
    }

    @Test
    void shouldRejectNullStatus() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CheckResult(
                        "Java check",
                        null,
                        "Missing status."
                )
        );
    }

    @Test
    void shouldRejectNullCategory() {
        assertThrows(
                NullPointerException.class,
                () -> new CheckResult(
                        "Java check",
                        CheckResult.Status.PASS,
                        "Available",
                        null,
                        CheckResult.Severity.INFO,
                        10,
                        "",
                        Instant.now()
                )
        );
    }

    @Test
    void shouldRejectNullSeverity() {
        assertThrows(
                NullPointerException.class,
                () -> new CheckResult(
                        "Java check",
                        CheckResult.Status.PASS,
                        "Available",
                        CheckResult.Category.ENVIRONMENT,
                        null,
                        10,
                        "",
                        Instant.now()
                )
        );
    }

    @Test
    void shouldRejectNegativeDuration() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new CheckResult(
                        "Build",
                        CheckResult.Status.FAIL,
                        "Failed",
                        CheckResult.Category.BUILD,
                        CheckResult.Severity.HIGH,
                        -1,
                        "BUILD_FAILED",
                        Instant.now()
                )
        );
    }

    @Test
    void shouldReplaceNullDetailsAndErrorCode() {
        CheckResult result = new CheckResult(
                "Git check",
                CheckResult.Status.WARN,
                null,
                CheckResult.Category.GIT,
                CheckResult.Severity.LOW,
                20,
                null,
                Instant.now()
        );

        assertEquals("", result.details());
        assertEquals("", result.errorCode());
    }

    @Test
    void shouldGenerateTimestampWhenMissing() {
        CheckResult result = new CheckResult(
                "Java check",
                CheckResult.Status.PASS,
                "Available",
                CheckResult.Category.ENVIRONMENT,
                CheckResult.Severity.INFO,
                10,
                "",
                null
        );

        assertNotNull(result.checkedAt());
    }

    @Test
    void shouldConvertResultToStructuredMap() {
        CheckResult result = CheckResult.fail(
                "Maven build",
                CheckResult.Category.BUILD,
                CheckResult.Severity.HIGH,
                "Compilation failed.",
                "BUILD_FAILED",
                1250
        );

        Map<String, Object> data = result.toMap();

        assertEquals("Maven build", data.get("name"));
        assertEquals("FAIL", data.get("status"));
        assertEquals("Compilation failed.", data.get("details"));
        assertEquals("BUILD", data.get("category"));
        assertEquals("HIGH", data.get("severity"));
        assertEquals(1250L, data.get("durationMillis"));
        assertEquals("BUILD_FAILED", data.get("errorCode"));
        assertNotNull(data.get("checkedAt"));
    }

   @Test
void shouldRejectNullStatusInDefaultSeverity() {
    assertThrows(
            IllegalArgumentException.class,
            () -> new CheckResult(
                    "Build",
                    null,
                    "Failed"
            )
    );
}
}

