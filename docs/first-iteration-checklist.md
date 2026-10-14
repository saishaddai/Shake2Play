# First iteration checklist

Goal: establish a minimal, runnable Shake2Play app foundation in small, verifiable steps.

Assumption: Android is the first runtime target. The local Android SDK path must be configured, and the current ARM64 iOS simulator target is not runnable on this Intel Mac. Motion detection and audio playback are deferred to a later feature slice.

## 1. Align project documentation

- [x] Update the architecture and project-map docs to use the generated module and source-set names (`shared`, `androidApp`, `iosApp`).
- [x] Replace unsupported README claims with the current configured features, or mark them as planned.
- [x] Record the actual Gradle validation commands and current platform limitations.

**Done when:** documented paths, targets, and commands match the checked-in Gradle configuration.

## 2. Run the untouched starter on Android

- [ ] Configure the Android SDK path locally without committing machine-specific configuration.
- [ ] Build the Android debug app.
- [ ] Launch it on an Android emulator or device.

**Done when:** the starter app opens successfully on Android.

## 3. Create the minimal shared app shell

- [ ] Replace the starter demo content with a simple Shake2Play home screen in `shared/src/commonMain`.
- [ ] Keep the shared `App()` entry point and avoid adding sensor or audio integrations in this step.
- [ ] Build and launch the app on Android.

**Done when:** the new shared screen renders successfully on Android.

## 4. Add the first meaningful shared test

- [ ] Add a common test for real state or behavior introduced by the app.
- [ ] Avoid placeholder tests that only exercise trivial arithmetic.
- [ ] Run the relevant shared test task and record the result.

**Done when:** the new test passes using the configured test task.

## 5. Record the verified baseline

- [ ] Document the commands that were verified for building, running, and testing.
- [ ] Note any platform-specific validation limitations.
- [ ] Record motion detection and sound playback as the next feature slice, including their platform boundaries.

**Done when:** another contributor can follow the documented steps to reproduce the verified baseline and see what is intentionally deferred.
