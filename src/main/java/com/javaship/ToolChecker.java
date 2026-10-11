
package com.javaship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class ToolChecker {

    private static final long TIMEOUT_SECONDS = 10;
    private static final long OUTPUT_READ_TIMEOUT_SECONDS = 2;
    private static final int MAX_OUTPUT_CHARS = 8192;

    private ToolChecker() {
        // Prevent unnecessary object creation.
    }

    public enum Status {
        AVAILABLE,
        NOT_FOUND,
        TIMEOUT,
        INTERRUPTED,
        CHECK_REQUIRED,
        INVALID_INPUT,
        EXECUTION_ERROR
    }

    public record ToolCheckResult(
            String toolName,
            Status status,
            String versionOutput,
            String message,
            Integer exitCode
    ) {
    }

    public static ToolCheckResult checkTool(
            String toolName,
            String command
    ) {
        if (toolName == null || toolName.isBlank()) {
            return result(
                    "Unknown",
                    Status.INVALID_INPUT,
                    "",
                    "Tool name cannot be blank.",
                    null
            );
        }

        List<String> commandParts = resolveCommand(command);

        if (commandParts == null) {
            return result(
                    toolName,
                    Status.INVALID_INPUT,
                    "",
                    "Unsupported tool command: " + command,
                    null
            );
        }

        final Process process;

        try {
            process = new ProcessBuilder(commandParts)
                    .redirectErrorStream(true)
                    .start();

        } catch (IOException e) {
            return result(
                    toolName,
                    Status.EXECUTION_ERROR,
                    "",
                    "Could not start the tool. Check its installation, "
                            + "PATH, and permissions. Details: "
                            + e.getMessage(),
                    null
            );
        }

        ExecutorService readerExecutor =
                Executors.newSingleThreadExecutor(task -> {
                    Thread thread = new Thread(
                            task,
                            "javaship-output-reader"
                    );
                    thread.setDaemon(true);
                    return thread;
                });

        Future<String> outputFuture = null;

        try {
            InputStream processOutput = process.getInputStream();

            // Read output concurrently so a full process pipe
            // cannot prevent the child process from finishing.
            outputFuture = readerExecutor.submit(
                    () -> readBoundedOutput(processOutput)
            );

            boolean finished = process.waitFor(
                    TIMEOUT_SECONDS,
                    TimeUnit.SECONDS
            );

            if (!finished) {
                process.destroyForcibly();

                // Do not wait indefinitely for forced termination.
                process.waitFor(2, TimeUnit.SECONDS);

                return result(
                        toolName,
                        Status.TIMEOUT,
                        getPartialOutput(outputFuture),
                        "The tool did not finish within "
                                + TIMEOUT_SECONDS + " seconds.",
                        null
                );
            }

            String output = outputFuture.get(
                    OUTPUT_READ_TIMEOUT_SECONDS,
                    TimeUnit.SECONDS
            );

            int exitCode = process.exitValue();

            if (exitCode == 0 && !output.isBlank()) {
                return result(
                        toolName,
                        Status.AVAILABLE,
                        output.trim(),
                        "Tool executed successfully.",
                        exitCode
                );
            }

            String message;

            if (exitCode != 0) {
                message = "The tool exited with code "
                        + exitCode + ".";
            } else {
                message = "The tool returned no version information.";
            }

            return result(
                    toolName,
                    Status.CHECK_REQUIRED,
                    output.trim(),
                    message,
                    exitCode
            );

        } catch (InterruptedException e) {
            process.destroyForcibly();

            Thread.currentThread().interrupt();

            return result(
                    toolName,
                    Status.INTERRUPTED,
                    "",
                    "The tool check was interrupted.",
                    null
            );

        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            String details = cause == null
                    ? "Unknown error."
                    : String.valueOf(cause.getMessage());

            return result(
                    toolName,
                    Status.EXECUTION_ERROR,
                    "",
                    "Could not read tool output: " + details,
                    null
            );

        } catch (TimeoutException e) {
            process.destroyForcibly();

            return result(
                    toolName,
                    Status.EXECUTION_ERROR,
                    "",
                    "Timed out while collecting tool output.",
                    null
            );

        } finally {
            if (process.isAlive()) {
                process.destroyForcibly();
            }

            // Close the stream to help unblock a reader
            // if the process ended unexpectedly.
            try {
                process.getInputStream().close();
            } catch (IOException ignored) {
                // Cleanup must not hide the original result.
            }

            if (outputFuture != null && !outputFuture.isDone()) {
                outputFuture.cancel(true);
            }

            readerExecutor.shutdownNow();
        }
    }

    private static List<String> resolveCommand(String command) {
        if (command == null || command.isBlank()) {
            return null;
        }

        return switch (command.trim().toLowerCase(Locale.ROOT)) {
            case "java" ->
                    List.of("java", "--version");

            case "maven", "mvn" ->
                    List.of("mvn", "--version");

            case "git" ->
                    List.of("git", "--version");

            default -> null;
        };
    }

    private static String readBoundedOutput(InputStream inputStream)
            throws IOException {

        StringBuilder output = new StringBuilder();
        char[] buffer = new char[1024];
        boolean truncated = false;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        inputStream,
                        Charset.defaultCharset()
                )
        )) {
            int count;

            while ((count = reader.read(buffer)) != -1) {
                int remaining = MAX_OUTPUT_CHARS - output.length();
                int charactersToKeep = Math.min(count, remaining);

                if (charactersToKeep > 0) {
                    output.append(buffer, 0, charactersToKeep);
                }

                if (charactersToKeep < count) {
                    truncated = true;
                }

                // Continue draining the stream even after reaching
                // the storage limit, to avoid blocking the process.
            }
        }

        if (truncated) {
            output.append(System.lineSeparator())
                    .append("[Output truncated after ")
                    .append(MAX_OUTPUT_CHARS)
                    .append(" characters.]");
        }

        return output.toString();
    }

    private static String getPartialOutput(
            Future<String> outputFuture
    ) {
        try {
            return outputFuture.get(
                    OUTPUT_READ_TIMEOUT_SECONDS,
                    TimeUnit.SECONDS
            ).trim();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Partial output unavailable.";

        } catch (ExecutionException | TimeoutException e) {
            outputFuture.cancel(true);
            return "Partial output unavailable.";
        }
    }

    private static ToolCheckResult result(
            String toolName,
            Status status,
            String output,
            String message,
            Integer exitCode
    ) {
        return new ToolCheckResult(
                toolName,
                status,
                output,
                message,
                exitCode
        );
    }

    public static void runChecks() {
        System.out.println("\n--- Development Tool Checker ---");

        checkAndDisplay("Java", "java");
        checkAndDisplay("Maven", "maven");
        checkAndDisplay("Git", "git");

        System.out.println("\nTool checks completed.");
    }

    private static void checkAndDisplay(
            String toolName,
            String command
    ) {
        System.out.println("\nChecking " + toolName + "...");

        ToolCheckResult result = checkTool(toolName, command);

        System.out.println("Status: " + result.status());

        if (!result.versionOutput().isBlank()) {
            System.out.println("Version information:");
            System.out.println(result.versionOutput());
        }

        if (!result.message().isBlank()) {
            System.out.println("Details: " + result.message());
        }

        if (result.exitCode() != null) {
            System.out.println("Exit code: " + result.exitCode());
        }
    }
}
