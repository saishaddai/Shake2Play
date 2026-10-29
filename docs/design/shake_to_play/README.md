# Shake to Play

The Shake to Play design is the selected-sound flow for explicit shake listening and playback feedback. Its image is a visual reference; design-only sample titles and metadata are not app content.

## Design files

- [Screen image](./screen.png)
- [HTML reference](./code.html)
- [Design system](./DESIGN.md)

## Agreed scope

- Show the sound selected in Sound Library and provide back navigation to the library.
- Use the existing explicit listening control and represent listening, playback, unavailable-sensor, and playback-error states.
- Keep preview/manual playback available when shake detection is unavailable.
- Do not show sample titles such as “Midnight Pulse” or “Violet Resonance”, nor sample duration/category values, unless they are actual sound data.
- Do not add a persistent bottom tab bar; the flow is entered by selecting a sound and exited with back navigation.

## Existing implementation

| Design behavior | Project name | Status |
| --- | --- | --- |
| Selected sound identity | `SoundboardState.selectedSound` and `SoundItem.title` | Existing state and model; used by the current shared screen. |
| Shake listening | `SoundboardPresenter.startListening()`; `ShakeDetector` | Existing shared orchestration and platform contract. Android adapter is `AndroidShakeDetector`. |
| Playback and status | `PlaybackState`, `PlaybackFailure`, and `SoundboardPresenter` | Existing state and shared status/error handling. |
| Audio playback | `SoundPlayer` | Existing capability contract; Android implementation is `AndroidSoundPlayer`. |
| Return to Sound Library | No navigation state or separate screen yet | Planned; use back navigation when the two flows are split. |
| Duration, category, active clip card | No corresponding metadata on `SoundItem` | Deferred until backed by reliable sound metadata and actual playback state. |

`SoundPlayer` names the playback capability, not a screen. `ShakeToPlayScreen` is the proposed composable name for future UI, not an existing project symbol.