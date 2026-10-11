package com.javaship;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public record CheckResult(
        String name,
        Status status,
        String details,
        Category category,
        Severity severity,
        long durationMillis,
        String errorCode,
        Instant checkedAt
) {

    public enum Status {
        PASS,
        FAIL,
        WARN
    }

    public enum Category {
        ENVIRONMENT,
        BUILD,
        TESTS,
        DEPENDENCIES,
        GIT,
        DOCUMENTATION,
        SECURITY,
        PACKAGING,
        GENERAL
    }

    public enum Severity {
        INFO,
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    // Validate the result when it is created.
    public CheckResult {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Check name cannot be blank."
            );
        }

        Objects.requireNonNull(
                status, "Check status cannot be null."
        );

        Objects.requireNonNull(
                category, "Check category cannot be null."
        );

        Objects.requireNonNull(
                severity, "Check severity cannot be null."
        );

        if (durationMillis < 0) {
            throw new IllegalArgumentException(
                    "Duration cannot be negative."
            );
        }

        details = details == null ? "" : details;
        errorCode = errorCode == null ? "" : errorCode;

        checkedAt = checkedAt == null
                ? Instant.now()
                : checkedAt;
    }

    // Backward-compatible constructor with three arguments.
    public CheckResult(
            String name,
            Status status,
            String details
    ) {
        this(
                name,
                status,
                details,
                Category.GENERAL,
                defaultSeverity(status),
                0,
                "",
                Instant.now()
        );
    }

    // Create a successful check result.
    public static CheckResult pass(
            String name,
            Category category,
            String details,
            long durationMillis
    ) {
        return create(
                name,
                Status.PASS,
                category,
                Severity.INFO,
                details,
                durationMillis,
                ""
        );
    }

    // Create a failed check result.
    public static CheckResult fail(
            String name,
            Category category,
            Severity severity,
            String details,
            String errorCode,
            long durationMillis
    ) {
        return create(
                name,
                Status.FAIL,
                category,
                severity,
                details,
                durationMillis,
                errorCode
        );
    }

    // Create a warning result.
    public static CheckResult warn(
            String name,
            Category category,
            String details,
            long durationMillis
    ) {
        return create(
                name,
                Status.WARN,
                category,
                Severity.MEDIUM,
                details,
                durationMillis,
                ""
        );
    }

    // Shared method for constructing results.
    private static CheckResult create(
            String name,
            Status status,
            Category category,
            Severity severity,
            String details,
            long durationMillis,
            String errorCode
    ) {
        return new CheckResult(
                name,
                status,
                details,
                category,
                severity,
                durationMillis,
                errorCode,
                Instant.now()
        );
    }

    // Choose a default severity for the three-argument constructor.
    private static Severity defaultSeverity(Status status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Check status cannot be null."
            );
        }

        return switch (status) {
            case PASS -> Severity.INFO;
            case WARN -> Severity.MEDIUM;
            case FAIL -> Severity.HIGH;
        };
    }

    // Convenience methods for checking result status.
    public boolean isSuccessful() {
        return status == Status.PASS;
    }

    public boolean isFailure() {
        return status == Status.FAIL;
    }

    public boolean isWarning() {
        return status == Status.WARN;
    }

    public boolean isBlocking() {
        return status == Status.FAIL;
    }

    // Convert the result into structured data for reports.
    public Map<String, Object> toMap() {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("name", name);
        result.put("status", status.name());
        result.put("details", details);
        result.put("category", category.name());
        result.put("severity", severity.name());
        result.put("durationMillis", durationMillis);
        result.put("errorCode", errorCode);
        result.put("checkedAt", checkedAt.toString());

        return result;
    }
}
