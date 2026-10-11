package com.javaship;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReleaseChecker {

    private ReleaseChecker() {
        // Prevent unnecessary objects.
    }

    public static List<CheckResult> runChecks() {
        List<CheckResult> results = new ArrayList<>();

        System.out.println("================================");
        System.out.println("     JAVASHIP RELEASE CHECK");
        System.out.println("================================");

        checkReadme(results);
        checkMavenBuild(results);

        return results;
    }

    private static void checkReadme(List<CheckResult> results) {
        long startTime = System.nanoTime();

        Path readmePath = Path.of("README.md");
        long duration;

        if (Files.isRegularFile(readmePath)) {
            duration = (System.nanoTime() - startTime) / 1_000_000;

            results.add(CheckResult.pass(
                    "README documentation",
                    CheckResult.Category.DOCUMENTATION,
                    "README.md exists.",
                    duration
            ));
        } else {
            duration = (System.nanoTime() - startTime) / 1_000_000;

            results.add(CheckResult.warn(
                    "README documentation",
                    CheckResult.Category.DOCUMENTATION,
                    "README.md was not found in the project root.",
                    duration
            ));
        }
    }

    private static void checkMavenBuild(List<CheckResult> results) {
        long startTime = System.nanoTime();

        Process process = null;

        try {
            String osName = System.getProperty("os.name")
        .toLowerCase();

String mavenCommand = osName.contains("win")
        ? "mvn.cmd"
        : "mvn";

        ProcessBuilder builder = new ProcessBuilder(
        mavenCommand,
        "-B",
        "-DskipTests",
        "compile"
);
            builder.directory(Path.of(".").toAbsolutePath().toFile());
            builder.redirectErrorStream(true);
            builder.redirectOutput(ProcessBuilder.Redirect.DISCARD);

            process = builder.start();

            boolean finished = process.waitFor(60, TimeUnit.SECONDS);
            long duration = (System.nanoTime() - startTime) / 1_000_000;

            if (!finished) {
                process.destroyForcibly();

                results.add(CheckResult.fail(
                        "Maven compilation",
                        CheckResult.Category.BUILD,
                        CheckResult.Severity.HIGH,
                        "Maven compilation timed out after 60 seconds.",
                        "BUILD_TIMEOUT",
                        duration
                ));

                return;
            }

            int exitCode = process.exitValue();

            if (exitCode == 0) {
                results.add(CheckResult.pass(
                        "Maven compilation",
                        CheckResult.Category.BUILD,
                        "Project compiled successfully.",
                        duration
                ));
            } else {
                results.add(CheckResult.fail(
                        "Maven compilation",
                        CheckResult.Category.BUILD,
                        CheckResult.Severity.HIGH,
                        "Maven compilation failed. Run 'mvn compile' to inspect the errors.",
                        "BUILD_FAILED",
                        duration
                ));
            }

        } catch (IOException e) {
            long duration = (System.nanoTime() - startTime) / 1_000_000;

            results.add(CheckResult.fail(
                    "Maven compilation",
                    CheckResult.Category.BUILD,
                    CheckResult.Severity.HIGH,
                    "Could not start Maven. Check that Maven is installed and available on PATH.",
                    "MAVEN_NOT_AVAILABLE",
                    duration
            ));

        } catch (InterruptedException e) {
            if (process != null) {
                process.destroyForcibly();
            }

            Thread.currentThread().interrupt();

            long duration = (System.nanoTime() - startTime) / 1_000_000;

            results.add(CheckResult.fail(
                    "Maven compilation",
                    CheckResult.Category.BUILD,
                    CheckResult.Severity.HIGH,
                    "The Maven check was interrupted.",
                    "BUILD_INTERRUPTED",
                    duration
            ));
        }
    }

    public static void displayResults(List<CheckResult> results) {
        System.out.println("\n========== RELEASE REPORT ==========");

        for (CheckResult result : results) {
            System.out.println(
                    "[" + result.status() + "] "
                            + result.name()
                            + " - " + result.details()
                            + " (" + result.durationMillis() + " ms)"
            );
        }

        System.out.println("====================================");
    }
}
