# Third iteration checklist

Goal: evolve the current shared soundboard into the two designed flows—[Sound Library](design/sound_library/) and [Shake to Play](design/shake_to_play/)—while keeping shared UI and behavior in `commonMain` and native capabilities behind platform boundaries.

Assumption: the five bundled clips and Android adapters are the current functional baseline. The designs are visual references, not proof that their pictured sample content or every control is an agreed feature. The second iteration's device-level verification is still outstanding; complete that baseline check before relying on hardware behavior.

## 1. Turn the designs into implementable requirements

- [ ] Use **Sound Library** and **Shake to Play** consistently for the two flows; keep the Kotlin names tied to their behavior.
- [ ] Confirm which visible actions are in scope: library search, bottom navigation, and the recording/import action pictured in Sound Library.
- [ ] Confirm the source of sound artwork and any additional clips. Keep shipped media local; do not use the design HTML's remote image URLs at runtime.
- [ ] Decide whether duration and category are required sound metadata. Do not copy the mockup's sample values (such as “04:22”, “Ambient”, or “Violet Resonance”) into the five existing clips without real metadata.
- [ ] Review the existing design-to-code map and record accepted behavior and deferred mockup-only elements in the per-screen READMEs.

**Done when:** each screen's controls and content have a defined behavior and data source, and unsupported or unapproved details are explicitly excluded.

## 2. Establish the shared design foundation

- [ ] Derive the colors, typography, spacing, surfaces, and shapes to implement from the design documents; avoid maintaining conflicting screen-specific versions of shared tokens.
- [ ] Implement the shared styles with the Compose APIs and resources already configured for the project; add dependencies only if an accepted design requirement cannot be met otherwise.
- [ ] Ensure the dark visual treatment, contrast, readable text scaling, and interactive states work across the configured Android and iOS shared UI targets.
- [ ] Keep decorative effects such as blur and glow optional where they would reduce readability or are not supported consistently.

**Done when:** reusable Compose styling expresses the agreed design system and can be used by both flows without compromising accessibility or target compatibility.

## 3. Implement the Sound Library flow

- [ ] Create a focused shared `SoundLibraryScreen` composable using the approved content and the Sound Library design as its visual reference.
- [ ] Show the actual available `SoundItem` entries; use bundled/local artwork only when approved assets exist, and do not imply that 20 clips are loaded when only five are present.
- [ ] Wire item selection and preview to `SoundboardPresenter` and the existing shared soundboard state.
- [ ] Implement search, navigation controls, and recording/import only if accepted in step 1; each displayed control must perform its stated action.
- [ ] Preserve loading/empty and playback-error feedback where applicable, plus accessibility semantics for selection and preview.

**Done when:** users can browse the real sound library, select a sound, and preview it; every control shown in this flow is functional or intentionally omitted.

## 4. Implement the Shake to Play flow and screen transition

- [ ] Create a focused shared `ShakeToPlayScreen` composable using the matching design as a reference for the selected-sound and shake visualizer states.
- [ ] Connect selection-to-player and back navigation using the existing feature state; avoid adding a navigation dependency unless the resulting flow requires it.
- [ ] Render listening off/on, sound ready/playing, unavailable sensor, and playback failure states from `SoundboardState` and the existing capability contracts.
- [ ] Keep listening explicit and lifecycle-bound; retain manual preview when shake detection is unavailable.
- [ ] Show duration, category, or “currently active clip” details only when backed by actual model data and playback state.
- [ ] Keep the design's sample titles and device illustrations decorative unless product behavior and licensed/local assets are defined.

**Done when:** selecting a sound leads to a shared playback flow that reflects real capability and playback state, and users can return to the library without losing the intended selection.

## 5. Verify behavior and platform boundaries

- [ ] Add or update common tests for flow/navigation state, selection, preview, shake-triggered playback, unavailable capability, playback errors, and any accepted search behavior.
- [ ] Run `./gradlew :shared:testAndroidHostTest`, `./gradlew :shared:compileCommonMainKotlinMetadata`, and `./gradlew :androidApp:assembleDebug`.
- [ ] Install and launch the app on an Android emulator or device; verify both flows, manual preview, shake-to-play, back navigation, and lifecycle cleanup.
- [ ] Compile or link the iOS shared target where the host permits, and verify that the UI remains usable without iOS sensor/audio adapters.
- [ ] Update `docs/build-and-test.md`, the design-to-code map, and both screen READMEs with implemented names, behavior, validation results, and remaining platform limitations.

**Done when:** automated checks pass, the Android design flow is exercised on a device or emulator, and cross-platform UI and outstanding native capability work are accurately documented.

## Deferred beyond this iteration

- Implement and validate native iOS shake detection and audio playback.
- Add user-provided sound recording/import, library persistence, or network-backed media.
- Populate additional clips, artwork, categories, or duration metadata until their assets and content are approved and available locally.
- Add other library-management features not explicitly accepted in step 1.
