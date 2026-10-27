# Second iteration checklist

Goal: turn the shared soundboard shell into a first playable shake-to-sound experience while keeping platform behavior behind the existing KMP source-set boundaries.

Assumption: Android is the first runnable target, as in the first iteration. The initial baseline had an empty shared `SoundboardState`, no sound assets, and no sensor or audio adapters. This iteration targets a small Android vertical slice; iOS runtime support and user-managed sound libraries are follow-up work unless the product scope changes.

## 1. Decide the first playable behavior

- [x] Bundle the initial sample clips inside the app and play them from local resources only; do not download sounds or require network access. Defer user-provided audio and persistence.
- [x] Use audio-only `.m4a` files encoded as AAC-LC for the initial clips, stored in `shared/src/commonMain/composeResources/files/sounds` so Android and iOS adapters can load the same bundled assets through Compose Multiplatform resources. Verify playback on supported Android and iOS versions before treating that encoding as a cross-platform requirement.
- [x] Define what a shake does: on the selected sound screen, one shake triggers one playback for that sound while playback is active, and the same shake is ignored until playback finishes.
- [x] Identify the visible states for listening, playback, no available sensor, and playback failure in the shared soundboard state: `READY`, `PLAYING`, and the unavailable or error states remain platform-aware follow-up states.

**Done when:** local-only bundled playback is agreed, and shake behavior and required UI states are defined before platform APIs or UI are implemented.

## 2. Add shared soundboard and trigger behavior

- [x] Extend the shared sound model with only the metadata needed to identify and present the chosen sounds.
- [x] Put shared selection and trigger rules in the soundboard feature under `commonMain`.
- [x] Define narrow injected `ShakeDetector` and `SoundPlayer` contracts in `commonMain`; keep platform APIs out of shared code.
- [x] Add deterministic `commonTest` coverage for selection, trigger behavior, empty soundboards, shake availability, and manual-play fallback. Adapter orchestration tests with fakes belong with platform integration.

**Done when:** shared behavior is testable without sensors, audio hardware, or wall-clock delays.

## 3. Implement the Android capability adapters

- [x] Implement motion detection in `androidMain`, including lifecycle-aware accelerometer registration, shake filtering, and handling when a sensor is unavailable.
- [x] Implement playback in `androidMain` using the shared bundled sound resources only, with playback and data-source cleanup tied to the activity lifecycle and no runtime network fetch.
- [x] Wire lifecycle-bound adapters through `androidApp`; sensor listening starts only when requested and needs no additional platform permission.
- [x] Add common-test coverage for sound ID-to-resource mapping; keep hardware checks out of ordinary unit tests.

**Done when:** Android can provide motion events and play a selected sound through the shared contracts, with resources released when listening or playback ends.

## 4. Connect the shared screen

- [x] Replace the empty-state-only shell with the agreed sound list and selection/preview controls.
- [x] Add an explicit listening control and show whether shake detection is active.
- [x] Render empty, unavailable-sensor, and playback-error states without crashing or silently dropping the user's action.
- [x] Keep UI state and event handling in the feature's established presentation boundary; do not start sensor or playback work during composition.

**Done when:** the same shared screen can select and preview sounds and clearly represent the Android capability state. The Android app injects its lifecycle-bound adapters; platforms without injected adapters explain that shake detection or playback is unavailable.

## 5. Verify and record the Android slice

- [ ] Run `./gradlew :shared:testAndroidHostTest` and `./gradlew :androidApp:assembleDebug`.
- [ ] Install and launch on an Android emulator or device; verify manual playback, shake-triggered behavior, lifecycle cleanup, and the no-sensor path where available.
- [ ] Update `docs/build-and-test.md` with the commands and actual results; document that iOS adapters and runtime validation remain outstanding.

**Done when:** the Android vertical slice is reproducible from the documented commands, shared behavior tests pass, and remaining platform work is explicit.

## Deferred beyond this iteration

- Implement the corresponding native motion and audio adapters in `iosMain` and wire them through `iosApp`; validate on a supported Apple host or device.
- Add user-managed sound import, persistence, and library management.
- Tune shake sensitivity using real-device feedback after the basic trigger flow works.