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
| `./gradlew :androidApp:assembleDebug` | Build the Android debug app | Passed on 2026-10-26 with the Android sound player and shake detector |
| `./gradlew :shared:testAndroidHostTest` | Run common tests and Android host tests on the host JVM | Passed on 2026-10-26; includes bundled sound-resource mapping coverage |
| `./gradlew :shared:allTests` | Run configured shared tests across targets | Not completed on this Intel Mac; the aggregate includes iOS simulator work unavailable on this host |

Gradle task discovery with `./gradlew tasks --all` succeeded. The Android SDK is configured locally through `sdk.dir` in the ignored `local.properties`; do not commit machine-specific SDK paths.

## Run the Android app

Start an Android emulator or connect a device with USB debugging enabled, then run these commands from the repository root with the SDK's `platform-tools` directory on `PATH`:

```bash
adb devices
./gradlew :androidApp:installDebug
adb shell am start -n com.saishaddai/.MainActivity
```

The app was launched successfully on Android in checklist step 2. No device was attached during the 2026-10-20 verification, so the launch was not repeated alongside the build and test checks.

## Deferred feature boundary

The Android sensor and audio adapters are implemented in `androidMain` behind the shared capability contracts, and `androidApp` creates the lifecycle-bound adapters. Hardware playback, shake response, and lifecycle behavior still require validation on an Android device or emulator. The corresponding iOS adapters and runtime validation remain outstanding. Keep shared trigger rules and soundboard behavior in `commonMain`; keep hardware-dependent checks out of ordinary unit tests, and shared behavior testable in `commonTest` with deterministic fakes.

## Current host limitations

- The shared module configures `iosArm64` and `iosSimulatorArm64`. On the current `macos-x86_64` host, Gradle disables `iosSimulatorArm64Test` because its architecture does not match the host.
- The Android debug build and `:shared:testAndroidHostTest` pass with the configured SDK. iOS simulator test execution remains unavailable on this Intel host.
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
