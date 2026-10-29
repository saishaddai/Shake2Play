# Design-to-code map

This directory contains visual references for the shared soundboard. The designs describe two user flows, while the current Compose implementation is one screen. Treat the designs as targets, not as evidence that every pictured control or screen already exists.

## Screen names

| Design directory | Screen name | Role |
| --- | --- | --- |
| [`sound_library/`](./sound_library/) | Sound Library | Browse and select a sound, then preview it. |
| [`shake_to_play/`](./shake_to_play/) | Shake to Play | Show the selected sound and shake-triggered playback state. |

These names distinguish the library flow from the playback flow without calling either one a separate Kotlin screen before that UI is implemented.

## Existing implementation map

| Design element or behavior | Existing project element | Current status |
| --- | --- | --- |
| Sound library heading and list | `App()` in `shared/src/commonMain/kotlin/com/saishaddai/App.kt`; `SoundboardState.sounds` | Rendered as a list of buttons, not the pictured image grid. |
| Sound library search | No current search UI or state | Accepted for this iteration; not implemented yet. Search only the five bundled clips. |
| Sound item title | `SoundItem.title` in `feature/soundboard/SoundboardState.kt` | Implemented. |
| Selected sound | `SoundboardState.selectedSoundId` and `selectedSound` | Implemented. |
| Selecting an item | `SoundboardPresenter.selectSound()` | Implemented. |
| Preview action | `App()` preview button and `SoundboardPresenter.previewSelectedSound()` | Implemented for the injected `SoundPlayer`. |
| Listening state and shake status | `SoundboardState.isListening`, `shakeAvailability`; `App()` listening control and status message | Implemented. |
| Shake-triggered playback | `SoundboardPresenter.startListening()` with `ShakeDetector`; `SoundboardState.handleShake()` | Implemented through the Android adapter. |
| Playback status and errors | `PlaybackState`, `PlaybackFailure`, and `SoundboardPresenter` | Implemented; rendered in `App()`. |
| Audio playback boundary | `SoundPlayer` in `feature/soundboard/SoundboardCapabilities.kt`; Android implementation in `shared/src/androidMain/kotlin/com/saishaddai/feature/soundboard/AndroidSoundPlayer.kt` | Implemented for Android. |
| Duration and category labels | No current field on `SoundItem` or sound metadata source | Deferred until reliable values are available; do not populate with design-only sample values. |
| Artwork and additional clips | Five bundled sound resources; no approved local artwork | Keep the five clips; add no artwork or clips until approved local assets are provided. Do not load the HTML's remote image URLs at runtime. |
| Persistent bottom tabs and recording/import action | No corresponding shared UI or capability | Deferred; navigate between the two flows by selecting a sound and returning from playback. |
| Separate playback page | No corresponding shared UI or navigation element | Intended flow for this iteration; currently the soundboard is still one screen. |

`App()` currently owns the shared Compose presentation and constructs the `SoundboardPresenter`; it is not yet split into `SoundLibraryScreen` and `ShakeToPlayScreen` composables. `SoundPlayer` is a playback capability contract, not a screen.

## Implementation plan

1. **Keep a single vocabulary.** Use “Sound Library” and “Shake to Play” for the design flows. Keep Kotlin type and function names in Kotlin style, and name new UI elements after the behavior they own rather than the artwork.
2. **Preserve the current ownership boundaries.** Continue rendering shared UI in `commonMain`, keeping selection and playback state in the soundboard feature, and keeping sensor/audio APIs in platform adapters.
3. **Map before extracting.** When implementing the designs, first map each visible control to an existing state field or presenter action. Extract separate screen composables only when navigation or distinct screen state is actually introduced.
4. **Respect the agreed scope.** Search the existing bundled sound list. Defer bottom tabs and recording/import, and do not invent artwork, extra clips, categories, or durations. Keep all shipped media local.
5. **Implement and verify incrementally.** Apply the shared design tokens and components in the relevant Compose UI, retain empty/unavailable/error states, and add tests for any new behavior. Update this map when implemented names or behavior change.

## Design files

- [`sound_library/DESIGN.md`](./sound_library/DESIGN.md), [`sound_library/code.html`](./sound_library/code.html), and [`sound_library/screen.png`](./sound_library/screen.png)
- [`shake_to_play/DESIGN.md`](./shake_to_play/DESIGN.md), [`shake_to_play/code.html`](./shake_to_play/code.html), and [`shake_to_play/screen.png`](./shake_to_play/screen.png)
