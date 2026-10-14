# Build and test guidance

This file records validation for the generated Gradle project. Commands and limitations below reflect the current checked-in targets and the last verification on an Intel macOS host.

## General workflow

- Inspect the real Gradle configuration before running commands.
- Prefer the smallest relevant task for the changed area.
- Run broader verification only when the change affects shared contracts or cross-platform behavior.
- Record constraints if a target cannot be built in the current environment.

## Typical validation categories

- Shared logic: common unit tests
- Compose UI: UI tests or screenshot checks when available
- Platform integrations: target-specific compile or test tasks
- Interop: Kotlin-to-Swift validation for Apple targets when relevant

## Current Gradle commands

Run commands from the repository root using the checked-in wrapper:

| Command | Purpose | Current status |
| --- | --- | --- |
| `./gradlew :shared:compileCommonMainKotlinMetadata` | Compile shared common Kotlin metadata | Passed on 2026-10-12 |
| `./gradlew :androidApp:assembleDebug` | Build the Android debug app | Not verified: Android SDK path is empty in local `local.properties` |
| `./gradlew :shared:allTests` | Run configured shared and platform tests | Not verified: task graph requires the Android SDK; the run stopped before tests executed |

Gradle task discovery with `./gradlew tasks --all` succeeded. The Android SDK can be configured with `ANDROID_HOME` or a local `sdk.dir` entry in the ignored `local.properties`; do not commit machine-specific SDK paths.

## Current host limitations

- The shared module configures `iosArm64` and `iosSimulatorArm64`. On the current `macos-x86_64` host, Gradle disables `iosSimulatorArm64Test` because its architecture does not match the host.
- The Android build and tests currently stop because no Android SDK location is configured. This is an environment setup issue; it does not establish whether those tasks pass once the SDK is available.
- A successful common metadata compilation does not verify Android packaging, iOS framework linking, or app launch behavior.

## Good practices

- Use deterministic tests.
- Avoid real network calls or wall-clock timing in CI-critical checks.
- Keep fixtures and examples sanitized and non-sensitive.
- Treat build failures as an authoritative signal; do not guess around them.

## CI expectations

- The repository CI should validate the core project tasks that are required by the architecture.
- If a target is unsupported on the current host, document the limitation instead of claiming the check passed.
- Keep the task list aligned with the actual repository configuration.
